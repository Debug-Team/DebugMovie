var app = new Vue({
    el: '#app',
    data: {
        search: '',
        nameList: [],
        douban_need: 'https://images.weserv.nl/?url=',
        movies_all: []
    },
    methods:{
        querySearch(queryString, cb) {
            var nameList = this.nameList;
            var results = queryString ? nameList.filter(this.createFilter(queryString)) : nameList;
            // console.log(results)
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilter(queryString) {
            return (nameList) => {
                return (nameList.value.indexOf(queryString.toLowerCase()) === 0);
            };
        },
        loadAll() {
            return [{value: '肖申克的救赎'}, {value: '霸王别姬'}];
        },
        handleSelect(item) {
            // console.log(item);
        }
    },
    created () {
        //获取数据
        var data = loadMovies();
        //处理数据
        var movies = [];
        for(var key in data){
            // console.log(data[key]);
            movies.push(data[key]);
        }
        //按照三个网站的综合排名排序
        movies.sort((movie1, movie2) => {
            return movie1['rank'] - movie2['rank'];
        })
        console.log(movies)
        //添加到总网站名称里
        for(var index in movies){
            this.nameList.push({value: movies[index].title});
            //处理总排名
            movies[index].index = parseInt(index)+1;
            //去掉时光评分的分字
            if(movies[index]['rate_time'] != null){
                movies[index]['rate_time'] = movies[index]['rate_time'].replace('分', '');
            }
            //如果图片链接是豆瓣，加上第三方图片链接
            if(movies[index].img.indexOf('doubanio') > 0){
                movies[index].img = this.douban_need + movies[index].img;
            }
        }
        // console.log(this.nameList)

        this.movies_all = movies;
        // this.movies_all = [
        //     {
        //         index: 1, name: '肖申克的救赎', director: '', actors: '蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿', geners: '', date: '1994-10-14(美国)', image: 'https://p0.meituan.net/movie/283292171619cdfd5b240c8fd093f1eb255670.jpg@160w_220h_1e_1c',
        //         index_maoyan: 1, rate_maoyan: '9.5', numbers_maoyan: '',
        //         index_douban: 1, rate_douban: '9.5', numbers_douban: '1422653人评价',
        //         index_mtime: 1, rate_mtime: '9.5', numbers_mtime: ' 63249人评分',
        //     },
        //     {
        //         index: 2, name: '肖申克的救赎', director: '', actors: '蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿', geners: '', date: '1994-10-14(美国)', image: 'https://p0.meituan.net/movie/283292171619cdfd5b240c8fd093f1eb255670.jpg@160w_220h_1e_1c',
        //         index_maoyan: 1, rate_maoyan: '9.5', numbers_maoyan: '',
        //         index_douban: 1, rate_douban: '9.5', numbers_douban: '1422653人评价',
        //         index_mtime: 1, rate_mtime: '9.5', numbers_mtime: ' 63249人评分',
        //     },
        //     {
        //         index: 3, name: '肖申克的救赎', director: '', actors: '蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿', geners: '', date: '1994-10-14(美国)', image: 'https://p0.meituan.net/movie/283292171619cdfd5b240c8fd093f1eb255670.jpg@160w_220h_1e_1c',
        //         index_maoyan: 1, rate_maoyan: '9.5', numbers_maoyan: '',
        //         index_douban: 1, rate_douban: '9.5', numbers_douban: '1422653人评价',
        //         index_mtime: 1, rate_mtime: '9.5', numbers_mtime: ' 63249人评分',
        //     },
        //     {
        //         index: 4, name: '肖申克的救赎', director: '', actors: '蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿', geners: '', date: '1994-10-14(美国)', image: 'https://p0.meituan.net/movie/283292171619cdfd5b240c8fd093f1eb255670.jpg@160w_220h_1e_1c',
        //         index_maoyan: 1, rate_maoyan: '9.5', numbers_maoyan: '',
        //         index_douban: 1, rate_douban: '9.5', numbers_douban: '1422653人评价',
        //         index_mtime: 1, rate_mtime: '9.5', numbers_mtime: ' 63249人评分',
        //     },
        //     {
        //         index: 5, name: '肖申克的救赎', director: '', actors: '蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿', geners: '', date: '1994-10-14(美国)', image: 'https://p0.meituan.net/movie/283292171619cdfd5b240c8fd093f1eb255670.jpg@160w_220h_1e_1c',
        //         index_maoyan: 1, rate_maoyan: '9.5', numbers_maoyan: '',
        //         index_douban: 1, rate_douban: '9.5', numbers_douban: '1422653人评价',
        //         index_mtime: 1, rate_mtime: '9.5', numbers_mtime: ' 63249人评分',
        //     }
        // ]
    },
    // mounted () {
    //     this.nameList = this.loadAll();
    // },
    computed: {
        movies: function () {
            let searchKey = this.search;
            return this.movies_all.filter(function (item) {
                // console.log(item.name.indexOf(searchKey.toLowerCase()))
                // return item.name.indexOf(searchKey) !== -1;
                return item.title.indexOf(searchKey) !== -1;
            })
        }
    }
})

function loadMovies() {
    var result = {};
    $.ajax({
        url: 'getMovieInfo',
        method: 'get',
        async: false,
        success: function (data) {
            // console.log(data);
            result = data;
        },
        error: function (data) {
            console.error('error');
            console.error(data);
        }
    })
    console.log(result)
    return result;
}