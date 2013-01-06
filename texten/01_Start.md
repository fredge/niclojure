## Your own two hands and Clojure. 

###### Vin: Wine 
When the order has been made, the wine waiter comes and present the selection.

This book is about sharing love for Clojure, why it is used now, and it will be used for quite some time. You don't need to be a great IT geek, you just have to start using Clojure. 

So prepare your favorite text editor, and .... Here we go!

### Clojure References and links when you get lost

There are tutorials to learn the basic baby steps of Clojure around the web. This book do not want to reinvent the wheel so if you need you should have a look at some of them.

One of the most up-to-date could be:

[http://learn-clojure.com/clojure_tutorials.html](http://learn-clojure.com/clojure_tutorials.html)

I will not go too much into the first steps details, since this is not the aim of this book. There is a completely new online book written by [John](http://www.unexpected-vortices.com/clojure/brief-beginners-guide/index.html) that should get all the attention it deserves.

You could also do a few [clojure koans](https://github.com/functional-koans/clojure-koans) to make sure the basic are solid and fun for you.

Last one, if you get lost on your journey, be sure to refer to the [cheatsheet](http://clojuredocs.org/quickref/Clojure%20Core).

### Leiningen, your tool to run and distribute clojure code

#### Do you have your REPL with you ? 

Basically to run whatever Clojure code you want, we are going to use a Read-Eval-Print-Loop interactive environment. 

With a REPL, you first type a command, then the interpreter reads it, print the release even if there is none, then go and wait for your next command.

All commands stays in memory and you can refer to old processed commands and use their result, refactor and so on.

To start using clojure, we actually do not install it. We are going to install a tool named Leiningen, so most of the dependencies and environment problems will be solved and you can simply enjoy the core of the meal.

The home page for Leiningen and its related command line, *lein* can be found at the following location:

[https://github.com/technomancy/leiningen#installation](https://github.com/technomancy/leiningen#installation)

To install on unix like machines: 

* Download the [script](https://raw.github.com/technomancy/leiningen/preview/bin/lein)
* Place it on your $PATH.
* Set it to be executable. _(chmod 755 lein)_

To install on windows:

Most users can get the [batch](https://raw.github.com/technomancy/leiningen/preview/bin/lein.bat) file. 
If you have wget.exe or curl.exe already installed and in PATH, you can just run lein self-install, otherwise get the standalone jar from the downloads page. 

If you have Cygwin you should be able to use the shell script above rather than the batch file.

Once you have it installed, you can chek lein's version using the following command:

    lein version 

Which should output something similar to:

    [~/projects/niclojure/] % lein version 
    Leiningen 2.0.0-preview10 on Java 1.7.0_10 Java HotSpot(TM) 64-Bit Server VM

If things do not work load as expected, please refer to the leiningen documentation, check your internet connection, reboot and if all else fail ask the waiter.

Now that we are sure lein is properly installed, let's start having fun, let's run our Read-Eval-Print-Loop on our own machine:

    lein repl

This will print some stuff:

    [niko@Modrzyks-MacBook-Pro-2][11:42][~/projects/mascarpone/chapter01/] % lein repl
    nREPL server started on port 54311
    REPL-y 0.1.0-beta10
    Clojure 1.4.0
        Exit: Control+D or (exit) or (quit)
    Commands: (user/help)
        Docs: (doc function-name-here)
              (find-doc "part-of-name-here")
      Source: (source function-name-here)
              (user/sourcery function-name-here)
     Javadoc: (javadoc java-object-or-class-here)
    Examples from clojuredocs.org: [clojuredocs or cdoc]
              (user/clojuredocs name-here)
              (user/clojuredocs "ns-here" "name-here")
    user=> 

Voila ! Time for a glass of wine to congratulate ourself.

You are ready to try your first useful tricks in Clojure.

Now is the time to jump to the first samples in the Appendix A, go along and try it by yourself.

I insist. Try it by yourself *NOW* before the eggs are fried and the vinegar turns sour. 

#### Workflow

Most LISP-ified users have their own workflow while using the REPL. The workflow we expose in this section is only a proposition. Do what works best for you. 

What I find out works is

* Type in the REPL a few things to get variables and the result you want. 
* Save results one after the other in variables to go through them later
* Bit by bit, make funcions you can reuse, little block of code that makes your iteration easier and faster 
* Build on each of those function, each function should make something precise, with no side-effect
* Finish your one file/namespace just like an API, by exposing methods that can be used by regular users
* Finish with some documentation so it is easy to grasp what each function does, and what are the side effect.

#### You need to depend on someone

You knew it. As soon as your ordered the fois-gras, you knew you wanted some of that salmon salad with trout eggs on top.

In the Clojure world, to get code that other people have been writing you just write them to a file named _project.clj_ that lein is creating for you, then let leiningen do the rest.

To see what this file look like, we use the command:

    lein new <appname>

For example:

    lein new sample00

    [~/projects/mascarpone/] % lein new sample
    Generating a project called sample based on the 'default' template.
    To see other templates (app, lein plugin, etc), try `lein help new`.

Lein has generated a set of directories and files:

    .
    ├── README.md
    ├── doc
    │   └── intro.md
    ├── project.clj
    ├── src
    │   └── sample
    │       └── core.clj
    └── test
        └── sample
            └── core_test.clj
    5 directories, 5 files

We have a few markdown files for writing your own documentation, the clojure files where we will write code, and then the one we are interested in right now is _project.clj_

    (defproject sample "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [[org.clojure/clojure "1.4.0"]])

This is pure clojure code, so no need to learn a new weird syntax.I am sure you remember the Apache Ant days, or those more recent Apache Maven 10.0 awesome builds. If not, do not try to remember, read more of this book instead. ;)

So back to the code above, we have a _project_ with some metadata. All the metadata can be read in the [sample file](https://github.com/technomancy/leiningen/blob/master/sample.project.clj) provided on the Leiningen website.

For now, we just want to have a look on how to add a dependency　for your need. We will find out how to find the pattern for it in the next section, for now, we want to add a json parsing named cheshire library to our code:

    [cheshire "5.0.1"]

The project.clj file will now look like this:

    (defproject sample "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [
        [org.clojure/clojure "1.4.0"]
        [cheshire "5.0.1"]
        ])

The next time we start the REPL, lein will output, only once, so more messages:

    Could not find artifact cheshire:cheshire:pom:5.0.1 in central (http://repo1.maven.org/maven2)
    Retrieving cheshire/cheshire/5.0.1/cheshire-5.0.1.pom (3k)
        from https://clojars.org/repo/
    Retrieving com/fasterxml/jackson/core/jackson-core/2.1.1/jackson-core-2.1.1.pom (6k)
        from http://repo1.maven.org/maven2/
        ...
        ...
    Retrieving cheshire/cheshire/5.0.1/cheshire-5.0.1.jar (12k)
        from https://clojars.org/repo/

Should be pretty fast, unless you are on a modem connection in a hotel in country side California waiting in the lobby for your glass of wine to be served.

Now straight to our new downloaded library. Let's generate some JSON code with:

    (use 'cheshire.core)

As you remember from the Appendix samples, we can get the documentation for a function using _doc_:

    user=> (doc generate-string)
    -------------------------
    cheshire.core/generate-string
    ([obj] [obj opt-map])
      Returns a JSON-encoding String for the given Clojure object. Takes an
      optional date format string that Date objects will be encoded with.
      The default date format (in UTC) is: yyyy-MM-dd'T'HH:mm:ss'Z'
    nil

And from a regular Clojure sequence, we can create some valid json:

    user=> (generate-string '(1 2 3))
    "[1,2,3]"
    user=> (generate-string '{:name (1 2 3)})
    "{\"name\":[1,2,3]}"

And we can of course, do the opposite, parse some string and turn it into a valid Clojure sequence with:

    user=> (parse-string "{\"foo\":\"bar\"}")
    {"foo" "bar"}

Unlimited power. Time for a second sip of that easy to drink South of France Chardonay 2010.

### Ibiblio, Java libraries at your fingertips

You might be wondering where the cheshire dependency has been pulled from.
In the Java world, Maven, however good or bad, has managed to impose a clean way of storing and retrieving Java code packaged as java archives onto something named ibiblio.

[http://mirrors.ibiblio.org/maven2/](http://mirrors.ibiblio.org/maven2/)

In most cases pure java libraries, of which Clojure can make use straight away (yey!), will be retrieved from there. 

For example, you may remember the famous Apache library named common-io.
There is a [descriptor](http://mirrors.ibiblio.org/maven2/commons-io/commons-io/2.4/commons-io-2.4.pom) for it that can be found in something called a pom file. 

The file is a terrible amount of xml, of which we will only read the following section:

    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.4</version>

In our all easy Clojure world, this will translate to:

    [commons-io/commons-io "2.4"]

Now, without much more trouble, we interface to the java world with:

    user=> (org.apache.commons.io.FileUtils/readLines (java.io.File. "README.md") "UTF-8")
    #<ArrayList [# sample, , A Clojure library designed to ... well, that part is up to you., , ## Usage, , FIXME, , ## License, , Copyright © 2013 FIXME, , Distributed under the Eclipse Public License, the same as Clojure.]>

We will see more Java interop code along this book, for now you can refer to the [Clojure interop page](http://clojure.org/java_interop).

### Clojars, or your clojure libraries at your fingertips

A more clojure specific place to find your libraries is named [Clojar](https://clojars.org/).

To find the Cheshire library we used earlier on, we would access:

    https://clojars.org/search?q=cheshire

And have our search returning the following:

![Clojure](../images/clojar.png)

Most if not everyone in the Clojure community have given up and is only ordering from the Clojar infrastructure. All of the latest reliable code should be on it.

### Create code, and share it through clojars

### 書いたコードをClojarでシェア
### Eclipseで一仕事
### JarkでJVMをリロード知らず
### Jarkで激しくClojureスクリプティング
### 止めずにライブラリを追加する
### 内緒でScalaのコードを走らせる
### Javaのコードを走らせるっていうのは、ここだけの話
### Clojureのメソッドをhookeでラップする
### おいしいプラグインのスープLeiningen仕立て
### Rubyをもう一つ： Jruby 
### Leiningen用のプラグインを書いてみる
### clojure-contribって知ってる？ ヤバいよ、それ。
### サンプル 
### 自分専用のWebベース Clojureインタープリタを作る