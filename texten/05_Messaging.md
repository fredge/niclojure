## Messaging

Because your application will probably not be standalone, but will be talking to a horde of different systems, we have created the Messaging chapter. I had the experience, though, to connect a clojure system to an impossible to configure banking system. The messages were so big I was only hoping the messaging queues will not blow up. What Clojure brought to me is the incredible flexibility to just say, "Yes, I can handle that message." or "Yes I can handle that timing and react in realtime" and have a pretty good certitude that if a problem was coming in the integration, it was not coming from the people at the dining table that we are. 

Here, we would like to show you a bit of this, and give you some insurance about easiness, readability and performances. 

This section will go in a bit details how to connect to different middleware like RabbitMQ, MemCache, Redis, JMS... and a few others, so you can get started quickly to core level of your work.

### Clamq, or your clojure toolkit for JMS, AMQP and RabbitMQ

Here comes the most generic [JMS, AMQP/RabbitMQ](https://github.com/sbtourist/clamq) middle ware in the Clojure land. Clamq basically connects you to the most famous queuing system of these days, with the same client code, so you won't even face problems if you need to switch from one to the other, or actually if you have the chance to change from one to the other.

For a short example, we will use [ActiveMQ](http://activemq.apache.org/download.html), not only for efficiency but because it is probably the fatest way to get started.
Let's start by downloading the package from:
	
	http://activemq.apache.org/download.html

Or your favorite package manager.
Once expanded, we can go to the *bin* folder where all the binaries are installed. We will then start the system with the simple command:

	./activemq start

Once the system is started, you can go to your browser, and access the admin [web console](http://activemq.apache.org/web-console.html):

	http://localhost:8161/admin/

Where the login is:

	admin / admin

If all goes well, you should see the following screen:

![ActiveMQ](../images/chap05/activemq.png)

Now we are all ready for some quick examples. To start working with clamq, we will require two dependencies:

	[clamq/clamq-jms "0.4"]
	[clamq/clamq-activemq "0.4"]

One for the core of clamq, the other one for the extra activemq extension. We could as well work with a simple JMS provider, but I like ActiveMQ all the same.

Our code presents how to write a simple producer/consumer test using a remote messaging queue, that will be created in ActiveMQ:

@@@ ruby chapter05/src/clamq.clj @@@

If we look, at the admin web console, we can see the queue is empty before the code is run:

![Queue](../images/chap05/queue1.png)

and contains the message we sent using our small snippet:

![Queue](../images/chap05/queue2.png)

Our next example will be showing how to use a Topic. Remember a topic is the way to broadcast the same message to multiple subscribers. 

@@@ ruby chapter05/src/clamq_topic.clj @@@

And, we pretty much notice that the code was very similar that the one for queues with the extra addition of:

	{:pubSub true}

For more of the ActiveMQ fun, you can have a look at the [ActiveMQ test suite](https://github.com/sbtourist/clamq/blob/master/clamq-activemq/test/clamq/test/activemq_test.clj) and its related [jms suite](https://github.com/sbtourist/clamq/blob/master/clamq-jms/src/clamq/test/base_jms_test.clj).

Suiiiiite.

### I have my horse, ride your Camel, easy messaging bus with everyone

I took exampes from [Apache Camel from Clojure](https://github.com/denlab/apache-camel-clojure) and 
[Camel Routes with Clojure](https://github.com/hellonico/clj-camel) to create this really short introduction to using Apache Camel, which acts as a *versatile open-source integration framework* or how to pretty much connects any kind of protocol to any other one in a snap. Snaps.

#### Camel on files 

In the first example we are going to connect a file to .. a file. Not very useful you might say, but wait, we will get the basics and ride on a nice wave of communication.

For this tutorial, we will use a somewhat custom dependency of clj-camel (see above) that we have thrown in the pot of clojars. Let's grab it with:

	[clj-camel "1.0.1"]

Once our REPL is sorted out, let's go through the file to file connection:

@@@ ruby chapter05/src/camel_file.clj @@@

So we start by configuring routes the Camel way to point from an endpoint :from to and endpoint :to. 

Then we start the Camel context which is now able to listen to all the endpoints that were defined.

Programmatically we can check that when we send a message to the queue :from, we will get a new file created in the folder we have defined, here *in*.

The next obvious step is to check whether we can create a file in the folder *in* and see if it appears in folder *out*. On *nix machines we can use:

	echo "hello" in/test.txt

In Windows all the same we can create a simple text file, with some "hello" the world of wine content. And there we go:

![camel](../images/chap05/camel.png)

Files are created as we put some content in the in folder.

We have commented out the line:

	   ; [:to "log:com.mycompany.order?level=INFO"]

But if you put it back, you will see a logging message in the REPL showing that the message was properly processed. 

#### Camel on queues 

Our next example will extend our first example with some queues to connect to ActiveMQ since we put some time to set it up earlier on. The ActiveMQ endpoint is not part of the core camel components so we added that to our set of dependencies already with clj-camel.

@@@ ruby chapter05/src/camel_queue.clj @@@

Only one added line to register the ActiveMQ component. But then adding a file to our :from folder will send directly a message to active mq with the content of the message.

But this is not all. Camel has an impressive set of [default components](http://camel.apache.org/components.html) that you can use just by accessing them through their URL scheme. All this at your Clojure hands. 

### Sometimes you need some Quartz, to schedule your Clojure

This short section will in no way go over the extensive documentation that is available the official [Quartzite](http://clojurequartz.info/articles/guides.html) documentation, because it so very good as almost any of the other [Clojurewerkz](http://clojurewerkz.org/) libraries.

No this section will get you started over how to schedule things with it, and then you should go and see how you can do more.

Quartz is the Grandfather of them scheduler on the Java platform, but while powerful, it was a little bit awkward to use it. With some Clojure DSL added to the mix, now at last it's easy to integrate it in a robust manner into your Clojure application.

To add it to our project:

	[clojurewerkz/quartzite "1.0.1"]

Quartz is known to work with a set of three main objects:

* Job: the task to be executed
* Trigger: When to execute the task
* Scheduler: Responsible to execute task at times defined by a trigger

Now that we have the concepts defined we can have an example and give it a go:

@@@ ruby chapter05/src/quartzite.clj @@@

Voila. 
We have a production proven scheduling engine taking care of our tasks.
There is more to come with grouping jobs, persisted stores, etc... and everything is clearly written in the documentation so time to give it a try.

### When in need of portable cron jobs

Sometimes you do not really need an incredibly difficult system to schedule jobs, but just in need of hacking some code at regular intervals.
For this kind of hard moments, we have [cronj](https://github.com/zcaudate/cronj).

We add it to the project with:

	[cronj "0.6.1"]

And now we can use the familiar cron notation to schedule some so exciting printing jobs. Have a look.

@@@ ruby chapter05/src/cronj.clj @@@

Not that difficult to get our order to the waiter properly is it ? So, wine or Champagne ? 

### Redis in your clojure

#### Getting Ready for Redis

You have probably heard of [Redis](http://redis.io/topics/introduction) in the world of key value stores. Redis can contain way more than just a handful of key values, it also supports strings, hashes, lists sets and sorted sets.
It also supports master slave replication that is meant to be trivial to setup.

To install redis on OSX, use homebrew with:

	brew install redis

To install redis on other plateforms, download and run from:

	http://redis.io/download

And to start with the default configuration, just run:

	redis-server

#### Some command line pong

You will notice there is a *redis-cli* command available to you, that we can start as is.

	redis-cli

And some very basic things you can do straight from the command line to test that everything is more or less ok:

	redis 127.0.0.1:6379> ping
	PONG
	redis 127.0.0.1:6379> set mykey somevalue
	OK
	redis 127.0.0.1:6379> get mykey
	"somevalue"
	redis 127.0.0.1:6379> 

Voila. All the commands can be tested from here, and the help menu is for once, very useful. Just try

	help <tab>

To display the different help sections.

#### Some Redis to use Clojure

Now that our Redis server is ready, we can start by preparing our project for Redis with [labs-redis](
[Redis](https://github.com/wallrat/labs-redis-clojure)). To insert it in our project, we do:

	[labs-redis "0.1.0"]

We have prepared a simple example to store and retrieve values. Please have a red wine look at it:

@@@ ruby chapter05/src/redis.clj @@@

You will not that the simple methods without *@* sign will return futures, meaning evaluation will not be done straight away. Also, the values are returned as byte arrays, because this is the way Redis handles data, and that is one of the reason why it makes it so fast too.

We have prepared also a simple pub-sub example, because this is one very good way of using Redis for real time analysis and execution. 
You will need to start the following in one REPL session first:

@@@ ruby chapter05/src/redis_sub.clj @@@

It will start a client waiting for specific kind of messages, in this case "msgs", "msg2" ...

In another REPL, you would start the companion Clojure code with:

@@@ ruby chapter05/src/redis_pub.clj @@@

If all goes well, the REPL of the subscriber should start writing a few things like this:

	user=> (load-file "src/redis_sub.clj")
	R  msgs hello 0
	R  msgs hello 1
	R  msgs hello 2
	R  msgs hello 3
	R  msgs hello 4

Saying that the messages were received properly.

#### Alternative Client

We would not have been very complete without talking about the [Carmine](https://github.com/ptaoussanis/carmine) redis client.

For basic usage, it is slightly more convoluted than the other client we have been using, but it's actually slightly more robust.

This is how you add it to your project:

	[com.taoensso/carmine "1.6.0"]

And look at the basic pong example at:

@@@ ruby chapter05/src/carmine.clj @@@

The rest is up to read the excellent [carmine documentation](https://github.com/ptaoussanis/carmine) available online.

We particularly like the [Message Queue](https://github.com/ptaoussanis/carmine#message-queue) section. It simple and concise !

#### What does Redis solve ? 

Redis has a number of common key IT usages, such as:

* Caching, is the most obvious choice, when you want to store something that should be computed somewhere and be made available to a range of different machines.
* Queues, when you just want to receive values and handle them later on.
* Pub/Sub, when you want to have multiple clients aware of some data change
* Unique N Items, Redis has a direct command to uniquely add to sets, so it is easy to know the number of concurrent users at a given time.
* Counting, more generally, keeping stats of different kinds is an easy and recurrent pattern usage for Redis.

etc..

And way more. 
In the past, we have used Redis to enqueue messages coming from many concurrent clients as fast as possible and allow the processing to be done later on by another system, which proved to work quite well.

### Distribute your application state with zookeeper and avout
[Avout](https://github.com/AlexBaranosky/avout)

### Every one talks about Hadoop, so let's talk to it with Clojure
[Hadoop](HBase: http://twitch.nervestaple.com/2012/01/12/clojure-hbase/)

### Hadoop queries from Clojure with Cascalog
[https://github.com/nathanmarz/cascalog/wiki](https://github.com/nathanmarz/cascalog/wiki)
Hadoop Query from Clojure

### Basic Apple Push Notifications
[Apple Push Notifications](https://github.com/HEROLABS/herolabs-apns)

### Where would you be without a spyglass (and a proper memcache client)
[Memcache](http://clojurememcached.info/articles/getting_started.html)

### Calling SIP, calling clojure
[SIP](https://github.com/Ruiyun/cljain)

### Apache Cassandra, at your clojure tips
[Cassaforte](https://github.com/clojurewerkz/cassaforte)

### Simple Apache Thrift, powering Facebook, in Clojure
[Thrift from Clojure](http://thecomputersarewinning.com/post/simple-thrift-in-clojure/)

### Storm is a distributed realtime computation system, accessible from Clojure
[https://github.com/nathanmarz/storm/wiki/Clojure-DSL](https://github.com/nathanmarz/storm/wiki/Clojure-DSL)
