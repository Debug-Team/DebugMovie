import csv
from xml.dom import minidom

import requests
from requests import RequestException
import re
import os


def get_one_page(url):
    try:
        response = requests.get(url)
        if response.status_code == 200:
            return response.text
        return None
    except RequestException:
        return None


def parse_one_page(html):
    pattern = re.compile('<dd>.*?board-index.*?>(\d+)</i>.*?data-src="(.*?)".*?' +
                         'name.*?title="(.*?)".*?star">(.*?)</p>.*?releasetime">' +
                         '(.*?)</p>.*?score.*?integer">(.*?)</i>.*?fraction">(.*?)</i>.*?</dd>',re.S)
    items = re.findall(pattern,html)
    for item in items:
        yield {
            'index': item[0],
            'image': item[1],
            'title': item[2],
            'actors': item[3].strip()[3:],
            'time': item[4].strip()[5:],
            'score': item[5] + item[6],
        }


def write_to_csv(content):
    csvnames = '/resources/maoyan_movie.csv'
    flag = True
    with open(csvnames, 'a+', encoding='utf-8') as f:
        writer = csv.writer(f)
        for item in content:
            if flag:
                writer.writerow(('index', 'image', 'title', 'actors', 'time', 'score'))
            writer.writerow((item['index'], item['image'], item['title'], item['actors'], item['time'], item['score']))
        flag = False


# 保存为xml
def write_to_xml(tags, content):
    filename = os.getcwd()+"/src/main/java/com/nju/movie/py/resources/maoyan_movie.xml"

    print(filename)
    # 新建xml文档对象
    xml = minidom.Document()

    # 创建根节点
    root = xml.createElement('maoyanTop100MovieInfo')
    xml.appendChild(root)

    # 写入具体节点
    for item in content:
        movie_node = xml.createElement('movie')
        for i in range(len(tags)):
            tag_node = xml.createElement(tags[i])
            text = xml.createTextNode(str(item[tags[i]]))
            tag_node.appendChild(text)
            movie_node.appendChild(tag_node)
        root.appendChild(movie_node)

    # 写好之后，就需要保存文档了
    fb = open(filename, 'w')
    xml.writexml(fb, indent='\t', addindent='\t', newl='\n', encoding='utf-8')
    fb.close()


def crawl_maoyan_movie_top100():
    content = []
    for i in range(10):
        url = 'http://maoyan.com/board/4?offset=' + str(i*10)
        html = get_one_page(url)
        for item in parse_one_page(html):
            content.append(item)
    tags = ['index', 'image', 'title', 'actors', 'time', 'score']
    write_to_xml(tags, content)


if __name__ == '__main__':
    crawl_maoyan_movie_top100()
