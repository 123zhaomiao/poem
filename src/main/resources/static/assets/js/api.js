/*

 api.js
 1. 主要用于前端页面与后端的交互
 前端：HTML+CSS+JavaScript开发的浏览器识别的Web程序
 后端：Java开发服务程序，通过HTTP协议提供Web API接口
 前端和后端交互：通信协议用的是HTTP协议
 */
function creationRanking(id) {
    //HTTP Method : GET请求
    //HTTP URL : 请求地址（服务端提供的API接口）
    $.get({
        url: "/analyze/author_count",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            //echarts图表对象
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                tooltip: {},
                //柱状图的提示信息
                legend: {
                    data: ['首']
                },
                xAxis: {
                    data:[]
                },
                yAxis: {
                },
                series: [{
                    name: '创作数量',
                    type: 'line',
                    data:[]
                }]
            };
            for (var i=0; i< data.length; i++) {
                var authorCount  = data[i];
                options.xAxis.data.push(authorCount.author);
                options.series[0].data.push(authorCount.count);
            }
            //下一次重新渲染
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {
        }
    });
}


function cloudWorld(id) {
    $.get({
        url: "/analyze/word_clode",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                series: [{
                    type: 'wordCloud',
                    shape: 'pentagon',
                    left: 'center',
                    top: 'center',
                    width: '80%',
                    height: '80%',
                    right: null,
                    bottom: null,
                    sizeRange: [12, 60],
                    rotationRange: [-90, 90],
                    rotationStep: 45,
                    gridSize: 8,
                    drawOutOfBound: false,
                    textStyle: {
                        normal: {
                            fontFamily: 'sans-serif',
                            fontWeight: 'bold',
                            color: function () {
                                //rgb(r,g,b)
                                return 'rgb(' + [
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160)
                                ].join(',') + ')';
                            }
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    // Data is an array. Each array item must have name and value property.
                    data: []
                }]
            };
            for (var i=0 ;i< data.length; i++) {
                var wordCount = data[i];
                //wordCount => 词 ： 词频
                options.series[0].data.push({
                    name: wordCount.word,
                    value: wordCount.count,
                    textStyle: {
                        normal: {},
                        emphasis: {}
                    }
                });
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {

        }
    });
}