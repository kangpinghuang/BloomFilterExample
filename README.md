# BloomFilterExample

布隆过滤器（Bloom Filter）是1970年由布隆提出的。它实际上是一个很长的二进制矢量和一系列随机映射函数。布隆过滤器可以用于检索一个元素是否在一个集合中。
它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率（false positive probability）.
Google Guava library给出了一个实现：com.google.common.hash.BloomFilter.

## 应用场景
* 数据库中，一般使用BloomFilter做索引，以避免在硬盘中寻找不存在的条目。 
* 用爬虫抓取网页时对网页url去重也需要用到BloomFilter

## 使用
### 创建
* 创建的时候，需要指定预期的数据条数，也可以指定fpp(如果没有制定fpp，默认为0.03).
* 需要指定一个类型模板

### 接口
主要提供：
* add接口，用户将数据添加到布隆过滤器中
* mightContain接口，用于判断是否包含某个对象
