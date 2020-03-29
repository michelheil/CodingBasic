# Play around with Scala Futures

## Idea
Following the example given in [Medium Blog](https://medium.com/@wiemzin/when-we-want-to-run-a-simple-code-using-future-we-get-a-compile-error-that-we-need-an-execution-ba416fca3739) bz Wiem Zine. 

## Basic Example
The execution of the BasicExample returns the result
```shell script
2020-03-29T08:45:00.317 [main] Start Program
2020-03-29T08:45:00.360 [main] Continue
2020-03-29T08:45:00.360 [ForkJoinPool-1-worker-13] Starting Task-Hello
Hello
2020-03-29T08:45:00.360 [ForkJoinPool-1-worker-13] Finished Task-Hello
```

We can observe that
* main runs in a thread called [main]
* taskHello using a Future runs in a thread called [ForkJoinPool-1-worker-13] 

So, it’s clear now that the new thread runs the block of the code inside the future (taskHello) on the execution context
 which is in our case the global execution context of scala.
 
## Concurrent Example
The execution of the ConcurrentExample return the result
```shell script
2020-03-29T09:13:45.041 [ForkJoinPool-1-worker-9] Task#2
2020-03-29T09:13:45.040 [ForkJoinPool-1-worker-7] Task#4
2020-03-29T09:13:45.040 [ForkJoinPool-1-worker-11] Task#3
2020-03-29T09:13:45.040 [ForkJoinPool-1-worker-5] Task#5
2020-03-29T09:13:45.041 [ForkJoinPool-1-worker-13] Task#1
2020-03-29T09:13:45.040 [ForkJoinPool-1-worker-3] Task#6
2020-03-29T09:13:46.047 [ForkJoinPool-1-worker-5] Task#8
2020-03-29T09:13:46.047 [ForkJoinPool-1-worker-11] Task#7
2020-03-29T09:13:46.047 [ForkJoinPool-1-worker-9] Task#10
2020-03-29T09:13:46.047 [ForkJoinPool-1-worker-3] Task#11
2020-03-29T09:13:46.047 [ForkJoinPool-1-worker-7] Task#9
2020-03-29T09:13:46.049 [ForkJoinPool-1-worker-13] Task#12
2020-03-29T09:13:47.048 [ForkJoinPool-1-worker-5] Task#13
2020-03-29T09:13:47.048 [ForkJoinPool-1-worker-11] Task#14
2020-03-29T09:13:47.048 [ForkJoinPool-1-worker-3] Task#16
2020-03-29T09:13:47.048 [ForkJoinPool-1-worker-9] Task#15
2020-03-29T09:13:47.049 [ForkJoinPool-1-worker-7] Task#17
2020-03-29T09:13:47.050 [ForkJoinPool-1-worker-13] Task#18
2020-03-29T09:13:48.049 [ForkJoinPool-1-worker-5] Task#19
2020-03-29T09:13:48.049 [ForkJoinPool-1-worker-3] Task#20
2020-03-29T09:13:48.049 [ForkJoinPool-1-worker-11] Task#21
2020-03-29T09:13:48.049 [ForkJoinPool-1-worker-7] Task#22
2020-03-29T09:13:48.049 [ForkJoinPool-1-worker-9] Task#23
2020-03-29T09:13:48.051 [ForkJoinPool-1-worker-13] Task#24
2020-03-29T09:13:49.049 [ForkJoinPool-1-worker-5] Task#25
2020-03-29T09:13:49.050 [ForkJoinPool-1-worker-3] Task#26
2020-03-29T09:13:49.050 [ForkJoinPool-1-worker-11] Task#27
2020-03-29T09:13:49.050 [ForkJoinPool-1-worker-7] Task#28
2020-03-29T09:13:49.050 [ForkJoinPool-1-worker-9] Task#29
2020-03-29T09:13:49.051 [ForkJoinPool-1-worker-13] Task#30
```
It takes about `4` seconds using `6` threads.

### Adjusting the parallelism by setting number of threads
Add the following two lines to the `build.sbt` file
```shell script
fork in run := true
javaOptions += "-Dscala.concurrent.context.maxThreads=3"
```
and run `sbt run`. The result is 
```shell script
[info] running (fork) org.michael.coding.basic.scala.futures.ConcurrentExample 
[info] 2020-03-29T09:30:21.698 [ForkJoinPool-1-worker-3] Task#3
[info] 2020-03-29T09:30:21.698 [ForkJoinPool-1-worker-5] Task#1
[info] 2020-03-29T09:30:21.698 [ForkJoinPool-1-worker-1] Task#2
[info] 2020-03-29T09:30:22.705 [ForkJoinPool-1-worker-1] Task#5
[info] 2020-03-29T09:30:22.705 [ForkJoinPool-1-worker-3] Task#4
[info] 2020-03-29T09:30:22.707 [ForkJoinPool-1-worker-5] Task#6
[info] 2020-03-29T09:30:23.706 [ForkJoinPool-1-worker-1] Task#7
[info] 2020-03-29T09:30:23.706 [ForkJoinPool-1-worker-3] Task#8
[info] 2020-03-29T09:30:23.708 [ForkJoinPool-1-worker-5] Task#9
[info] 2020-03-29T09:30:24.706 [ForkJoinPool-1-worker-1] Task#10
[info] 2020-03-29T09:30:24.707 [ForkJoinPool-1-worker-3] Task#11
[info] 2020-03-29T09:30:24.708 [ForkJoinPool-1-worker-5] Task#12
[info] 2020-03-29T09:30:25.707 [ForkJoinPool-1-worker-1] Task#13
[info] 2020-03-29T09:30:25.707 [ForkJoinPool-1-worker-3] Task#14
[info] 2020-03-29T09:30:25.709 [ForkJoinPool-1-worker-5] Task#15
[info] 2020-03-29T09:30:26.708 [ForkJoinPool-1-worker-1] Task#16
[info] 2020-03-29T09:30:26.708 [ForkJoinPool-1-worker-3] Task#17
[info] 2020-03-29T09:30:26.709 [ForkJoinPool-1-worker-5] Task#18
[info] 2020-03-29T09:30:27.708 [ForkJoinPool-1-worker-1] Task#19
[info] 2020-03-29T09:30:27.708 [ForkJoinPool-1-worker-3] Task#20
[info] 2020-03-29T09:30:27.710 [ForkJoinPool-1-worker-5] Task#21
[info] 2020-03-29T09:30:28.709 [ForkJoinPool-1-worker-1] Task#22
[info] 2020-03-29T09:30:28.709 [ForkJoinPool-1-worker-3] Task#23
[info] 2020-03-29T09:30:28.711 [ForkJoinPool-1-worker-5] Task#24
[info] 2020-03-29T09:30:29.710 [ForkJoinPool-1-worker-1] Task#25
[info] 2020-03-29T09:30:29.710 [ForkJoinPool-1-worker-3] Task#26
[info] 2020-03-29T09:30:29.711 [ForkJoinPool-1-worker-5] Task#27
[info] 2020-03-29T09:30:30.710 [ForkJoinPool-1-worker-3] Task#28
[info] 2020-03-29T09:30:30.710 [ForkJoinPool-1-worker-1] Task#29
[info] 2020-03-29T09:30:30.712 [ForkJoinPool-1-worker-5] Task#30
```
It takes about `10` seconds using only `3` threads.
