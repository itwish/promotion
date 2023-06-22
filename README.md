# Promotion -计算最低促销价demo

## 设计思路
由于促销数据的不规则性以及混合促销等情况的存在，为了提高程序通用性，采用了类似流水线的方式，通过正则过滤原始数据并生成通用数据模型，然后组装对应的基本促销策略，计算最低价。

### 过程
1.分析电商促销原始数据  
2.抽象并设计通用数据模型  
3.编写正则表达式用于匹配,提取有效数据  
4.将原始数据转换为通用数据模型  
4.根据通用数据模型与促销策略编写最低价计算方法  

#### 测试数据
```json
[
  {
      "price":1169,
      "promotion":"购买至少1件时可享受优惠,满1249元减130元"
  },
  {
      "price":1099,
      "promotion":"满1499减400,满899减300,满499减150,满499减100,满99减15"
  },
  {
      "price": 2019,
      "promotion": "满1099元,省500元"
  },
  {
      "price": 629,
      "promotion": "每300减50,可跨店,上不封顶"
  },
  {
      "price": 309,
      "promotion": "每满300元,省50元"
  },
  {
      "price": 4948,
      "promotion": "每满299元，可减50元现金，最多可减1000元"
  },
  {
      "price": 1599,
      "promotion": "每满1399元，可减300元现金,每满1299元，可减100元现金"
  },
  {
      "price": 349,
      "promotion": "下单再享9.5折"
  },
  {
      "price": 2100,
      "promotion": "满1件,打4.28折"
  },
  {
      "price": 5319,
      "promotion": "满399享9.5折"
  },
  {
      "price": 339,
      "promotion": "满2件，总价打9.80折；满3件，总价打9.50折"
  },
  {
      "price": 2100,
      "promotion": "每300减50,可跨店,上不封顶,满1件,打4.28折,"
  },
  {
      "price": 339,
      "promotion": "满599元减30元，满999元减50元，满1888元减100元,满2件，总价打9.80折；满3件，总价打9.50折,"
  },
  {
      "price": 955,
      "promotion": "淘金币可抵18.78元起,"
  },
  {
      "price": 2304,
      "promotion": ""
  }
]

```
