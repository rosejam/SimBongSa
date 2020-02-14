import React, { Component } from "react";
import { Pie } from "react-chartjs-2";
// @ts-ignore
var palette = require('google-palette');
var convert = require('color-convert');
interface Props {
    labels: string[],
    data: number[]
    width: any,
    height: any
}
interface State { }

export default class PieGraph extends Component<Props, State> {
    state = {
        data: {
            labels: [],
            datasets: [
                {
                    label: "",
                    data: [],
                    backgroundColor: [],
                    borderWidth: 3
                },
            ]
        },
        options: {
            scales: {
                yAxes: [
                    {
                        ticks: {
                            beginAtZero: true
                        }
                    }
                ]
            }
        }
    };

    constructor(props: any) {
        super(props);
        const { labels, data } = props;
        this.state = {
            data: {
                labels: labels,
                datasets: [
                    {
                        label: "# of Votes",
                        data: data,
                        backgroundColor: [],
                        borderWidth: 3
                    },
                ]
            },
            options: {
                scales: {
                    yAxes: [
                        {
                            ticks: {
                                beginAtZero: true
                            }
                        }
                    ]
                }
            }
        }
    }

    componentDidMount() {
        const { generateBackgroundColor } = this;
        const { data } = this.state;
        const len = data.datasets[0].data.length;
        this.setState({ data: { ...data, datasets: [{ ...data.datasets[0], backgroundColor: generateBackgroundColor(len) }] } })
    }
    componentDidUpdate() {
        const { data } = this.state;

        console.log(data);
    }

    generateBackgroundColor = (numberOfItems: number): string[] => {
        // 아래 두 가지 라이브러리 사용, 첫 번째 : 무지개 색 만들기, 두 번째 : rgb->hsv 변환 후 s값을 반으로 줄여서 연하게 만들기 
        // https://www.npmjs.com/package/google-palette
        // https://www.npmjs.com/package/color-convert
        let list = palette(['rainbow'], numberOfItems);
        list = list.map((item: string) => '#' + convert.hsv.hex([convert.hex.hsv(item)[0], 50, 100]))
        return list
    }

    render() {
        const { width, height } = this.props;
        return (
            <div>
                <Pie
                    data={this.state.data}
                    width={width}
                    height={height}
                    options={{ maintainAspectRatio: false }} // width, height 커스텀 사이즈로 하기 위해선 옵션에서 maintainAspectRatio: false 설정
                />
            </div>
        );
    }
}
