# Object-Oriented Programming Assignment 2
## About
In this project we are creating a number of text files with random number of lines, then calculating the number of lines for each file.  
The calculation is done using various ways: using only the Main Thread, using an array of Threads and using ThreadPool.
## Time measures
### Time measured for 10 files:
Using only Main Thread: 2ms  
Using Threads: 12ms  
Using ThreadPool: 20ms  
### Time measured for 100 files:  
Using only Main Thread: 56ms  
Using Threads: 50ms  
Using ThreadPool: 43ms  
### Time measured for 1000 files:  
Using only Main Thread: 567ms  
Using Threads: 408ms  
Using ThreadPool: 352ms  
### Time measured for 4000 files:  
Using only Main Thread: 2102ms  
Using Threads: 1626ms  
Using ThreadPool: 1424ms  
## Explaining the differences  
As we see in time measure for 10 files, the fastest calculation time is by using the main thread.  
The reason is that starting a thread or a threadPool allocates resources from the operating system,
and without doing do, the main thread is able to complete the task.  
When we look at the rest of the calculations with 100 files and more, 
we can see that the calculations with threads or threadPool is faster than using only one thread.  
We can also notice that the calculation with threadPool is almost always faster than using threads.  
In general, the reason using threadPool can be faster than using threads, 
is that we can use the same thread for multiple tasks instead of creating a completely new thread.  
Another reason for faster calculation using threadPool in this particular project, 
is that we are using submit() method that receives a Callable instead of Runnable, 
so we can get the return value faster with get() method instead of 
implementing our own method like we did with threads.    
