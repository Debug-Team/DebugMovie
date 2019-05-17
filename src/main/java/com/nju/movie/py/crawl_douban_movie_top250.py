import csv
from xml.dom import minidom

import requests
from lxml import html
import os


def pares_page(content):
    basic_url = "https://movie.douban.com/top250?start="
    index = 1

    for i in range(10):
        con = requests.get(basic_url+str(i)).content
        sel = html.fromstring(con)
        # 所有的信息都在class属性为info的div标签里，可以先把这个节点取出来
        for j in sel.xpath('//div[@class="item"]'):
            pic = j.xpath('div[@class="pic"]/a/img/@src')[0]

            item = j.xpath('div[@class="info"]')[0]
            # 影片名称
            title = item.xpath('div[@class="hd"]/a/span[@class="title"]/text()')[0]
            info = item.xpath('div[@class="bd"]/p[1]/text()')

            # 导演演员信息
            people = info[0].replace(" ", "").replace("\n", "")
            director = people.split("   ")[0][3:]
            actors = people.split("   ")[1][3:]
            # 上映日期
            date = info[1].replace(" ", "").replace("\n", "").split("/")[0]
            # 制片国家
            country = info[1].replace(" ", "").replace("\n", "").split("/")[1]
            # 影片类型
            geners = info[1].replace(" ", "").replace("\n", "").split("/")[2]
            # 评分
            rate = j.xpath('//span[@class="rating_num"]/text()')[0]
            # 评论人数
            comCount = j.xpath('//div[@class="star"]/span[4]/text()')[0]
            # 储存结果
            content.append((index, pic, title, director, actors, rate, date, country, geners, comCount))
            index += 1

def write_to_csv(content):
    csvnames = './resources/douban_movie.csv'
    flag = True
    with open(csvnames, 'a+', encoding='utf-8') as f:
        writer = csv.writer(f)
        for item in content:
            if flag:
                writer.writerow(('index', 'image', 'title', 'director', 'actors', 'rate', 'date', 'country', 'geners', 'comCount'))
            writer.writerow((item[0], item[1], item[2], item[3], item[4], item[5], item[6], item[7], item[8], item[9]))
        flag = False


# 保存为xml
def write_to_xml(tags, content):
    filename = os.getcwd()+"/src/main/java/com/nju/movie/py/resources/douban_movie.xml"

    # 新建xml文档对象
    xml = minidom.Document()

    # 创建根节点
    root = xml.createElement('doubanTop100MovieInfo')
    xml.appendChild(root)

    # 写入具体节点
    for item in content:
        movie_node = xml.createElement('movie')
        for i in range(len(tags)):
            tag_node = xml.createElement(tags[i])
            text = xml.createTextNode(str(item[i]))
            tag_node.appendChild(text)
            movie_node.appendChild(tag_node)
        root.appendChild(movie_node)

    # 写好之后，就需要保存文档了
    fb = open(filename, 'w')
    xml.writexml(fb, indent='\t', addindent='\t', newl='\n', encoding='utf-8')
    fb.close()


def crawl_douban_movie_top250():
    basic_url = "https://movie.douban.com/top250?start="
    content = []
    pares_page(content)
    # 节点标签
    tags = ['index', 'image', 'title', 'director', 'actors', 'rate', 'date', 'country', 'geners', 'comCount']
    write_to_xml(tags, content)


if __name__ == '__main__':
    crawl_douban_movie_top250()
