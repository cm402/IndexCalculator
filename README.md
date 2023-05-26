## Project explanation
I've implemented 2 solutions to the following problem:

"For given book, and a list of keywords, return pages each keyword appears on"

The first solution "calculateIndexWithSet" converts the List of words on each page into a Hashset, allowing
for constant-time checking of each keyword. 

The second solution "calculateIndexSorted" sorts both the List of keywords, and each pages List of words, meaning
we only need to iterate each pages words once, checking if the current keyword matches the current word,
and iterating the current keyword if it's less than the current word.

## Testing
I've tested the following scenarios with unit tests of both methods:
- An empty book with no keywords returns an empty index
- An empty book with keywords returns an empty index
- A single page book with no keywords returns an empty index
- A single page book with some keywords that are in the page, and one that isn't found in the page.
- A book with multiple pages, one which contains a subset of the other pages words.
- A single page book with the words on the page shuffled, so they're not in alphabetical order.

I've made the assumption that we just want to return an empty Index in situations where no keywords are found,
however this could be changed to a special value(e.g. null) or an Exception to be thrown if required.

## Benchmark testing

- I've used a book with 100 pages for benchmark testing, each page containing 26 words with the order randomised on each page.
- Both methods were tested with 10 iterations, and 6 warm up iterations.

### Results
Benchmark                                                              Mode  Cnt  Score   Error  Units

IndexCalculatorBenchmarkTests.many_page_book_has_keywords_set_test     avgt   10  **0.150 ± 0.009  ms/op**

IndexCalculatorBenchmarkTests.many_page_book_has_keywords_sorted_test  avgt   10  **0.262 ± 0.005  ms/op**

### Conclusions
The results show that the set implementation outperforms the sorting implementation. This is due to the cost of sorting each page, and having to iterate over
the entire pages words rather than just the keywords in the way the set implementation does. This difference in efficiency becomes more evident as the number of words
on each page increases.

### Benchmark tests output
JMH version: 1.21
 VM version: JDK 17.0.6, OpenJDK 64-Bit Server VM, 17.0.6+10-LTS
 VM invoker: C:\Users\connor.macfarlane\.jdks\corretto-17.0.6\bin\java.exe
 VM options: -ea -Didea.test.cyclic.buffer.size=1048576 -javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.3.2\lib\idea_rt.jar=57451:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.3.2\bin -Dfile.encoding=UTF-8
 Warmup: 6 iterations, 1 s each
 Measurement: 10 iterations, 10 s each
 Timeout: 10 min per iteration
 Threads: 1 thread, will synchronize iterations
 Benchmark mode: Average time, time/op
 Benchmark: org.example.IndexCalculatorBenchmarkTests.many_page_book_has_keywords_set_test

 Run progress: 0.00% complete, ETA 00:03:32
 Fork: 1 of 1
 Warmup Iteration   1: 0.158 ms/op

 Warmup Iteration   2: 0.300 ms/op

 Warmup Iteration   3: 0.175 ms/op

 Warmup Iteration   4: 0.185 ms/op

 Warmup Iteration   5: 0.190 ms/op

 Warmup Iteration   6: 0.187 ms/op
Iteration   1: 0.145 ms/op

Iteration   2: 0.148 ms/op

Iteration   3: 0.148 ms/op

Iteration   4: 0.144 ms/op

Iteration   5: 0.146 ms/op

Iteration   6: 0.150 ms/op

Iteration   7: 0.148 ms/op

Iteration   8: 0.151 ms/op

Iteration   9: 0.166 ms/op

Iteration  10: 0.150 ms/op


Result "org.example.IndexCalculatorBenchmarkTests.many_page_book_has_keywords_set_test":
0.150 ±(99.9%) 0.009 ms/op [Average]
(min, avg, max) = (0.144, 0.150, 0.166), stdev = 0.006
CI (99.9%): [0.140, 0.159] (assumes normal distribution)


 JMH version: 1.21
 VM version: JDK 17.0.6, OpenJDK 64-Bit Server VM, 17.0.6+10-LTS
 VM invoker: C:\Users\connor.macfarlane\.jdks\corretto-17.0.6\bin\java.exe
 VM options: -ea -Didea.test.cyclic.buffer.size=1048576 -javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.3.2\lib\idea_rt.jar=57451:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.3.2\bin -Dfile.encoding=UTF-8
 Warmup: 6 iterations, 1 s each
 Measurement: 10 iterations, 10 s each
 Timeout: 10 min per iteration
 Threads: 1 thread, will synchronize iterations
 Benchmark mode: Average time, time/op
 Benchmark: org.example.IndexCalculatorBenchmarkTests.many_page_book_has_keywords_sorted_test

 Run progress: 50.00% complete, ETA 00:02:00
 Fork: 1 of 1
 Warmup Iteration   1: 0.306 ms/op

 Warmup Iteration   2: 0.294 ms/op

 Warmup Iteration   3: 0.291 ms/op

 Warmup Iteration   4: 0.273 ms/op

 Warmup Iteration   5: 0.341 ms/op

 Warmup Iteration   6: 0.295 ms/op

Iteration   1: 0.262 ms/op

Iteration   2: 0.268 ms/op

Iteration   3: 0.262 ms/op

Iteration   4: 0.267 ms/op

Iteration   5: 0.262 ms/op

Iteration   6: 0.257 ms/op

Iteration   7: 0.261 ms/op

Iteration   8: 0.264 ms/op

Iteration   9: 0.260 ms/op

Iteration  10: 0.261 ms/op


Result "org.example.IndexCalculatorBenchmarkTests.many_page_book_has_keywords_sorted_test":
0.262 ±(99.9%) 0.005 ms/op [Average]
(min, avg, max) = (0.257, 0.262, 0.268), stdev = 0.003
CI (99.9%): [0.258, 0.267] (assumes normal distribution)


 Run complete. Total time: 00:03:59

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                                              Mode  Cnt  Score   Error  Units
IndexCalculatorBenchmarkTests.many_page_book_has_keywords_set_test     avgt   10  0.150 ± 0.009  ms/op
IndexCalculatorBenchmarkTests.many_page_book_has_keywords_sorted_test  avgt   10  0.262 ± 0.005  ms/op

Process finished with exit code 0