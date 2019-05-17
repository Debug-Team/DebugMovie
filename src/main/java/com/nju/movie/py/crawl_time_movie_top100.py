from xml.dom import minidom

import requests
from bs4 import BeautifulSoup
import re
import csv
import os


# 定义爬取函数
def get_infos(htmls, items):
    # 信息头
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36'
    }
    # flag在写入文件时判断是否为首行
    flag = True
    # 判断第一页网址，第二页及其后的网址
    for i in range(10):
        if i == 0:
            html = htmls
        else:
            html = htmls + 'index-{}.html'.format(str(i + 1))
        res = requests.get(html, headers=headers)
        soup = BeautifulSoup(res.text, 'lxml')
        alls = soup.select('#asyncRatingRegion > li')  # 选取网页的li节点的内容
        # 对节点内容进行循环遍历
        for one in alls:
            paiming = one.div.em.string  # 排名
            names = str(one.select('div.mov_pic > a'))  # 电影名称并将列表字符串化
            name = re.findall('.*?title="(.*?)">.*?', names, re.S)[0]  # 使用正则表达式提取内容
            image = re.findall('.*?src="(.*?)".*?', names, re.S)[0]  # 图片url

            content = str(one.select('div.mov_con > p.mt3'))  # 评论
            realcontent = re.findall('.*?mt3">(.*?)</p>', content, re.S)[0]  # 同上
            p1 = one.find(name='span', attrs={'class': 'total'}, text=re.compile('\d'))  # 评分在两个节点，
            p2 = one.find(name='span', attrs={'class': 'total2'}, text=re.compile('.\d'))
            # 判断评分是否为空
            if p1 and p2 != None:
                p1 = p1.string
                p2 = p2.string
            else:
                p1 = 'no'
                p2 = ' point'
            point = p1 + p2 + '分'
            numbers = one.find(text=re.compile('评分'))  # 评分数量

            peopleList = one.select('div.mov_con > p > a')
            director = peopleList[0].contents[0]  # 导演
            actorList = []  # 演员
            for i in range(len(peopleList)):
                if i != 0:
                    actorList.append(peopleList[i].contents[0])

            # 保存
            items.append((paiming, name, image, director, " ".join(actorList), realcontent, point, numbers))


# 保存为csv
def write_to_csv(content, csvname):
    csvnames = './resources/' + csvname + '.csv'
    flag = True
    with open(csvnames, 'a+', encoding='utf-8') as f:
        writer = csv.writer(f)
        for line in content:
            if flag:
                writer.writerow(('index', 'name', 'image', 'director', 'actors', 'realcontent', 'point', 'numbers'))
            writer.writerow((line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]))
            flag = False


# 保存为xml
def write_to_xml(tags, content):
    filename = os.getcwd()+"/src/main/resources/time_movie.xml"

    # 新建xml文档对象
    xml = minidom.Document()

    # 创建根节点
    root = xml.createElement('timeTop100MovieInfo')
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


def crawl_time_movie_top100():
    total_html = 'http://www.mtime.com/top/movie/top100/'
    csvname3 = 'time_movie'

    content = []
    get_infos(total_html, content)

    tags = ['index', 'name', 'image', 'director', 'actors', 'realcontent', 'point', 'numbers']
    write_to_xml(tags, content)


if __name__ == '__main__':
    crawl_time_movie_top100()
