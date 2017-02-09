# downloader
Coding problem

Write a program that can be used to download data from multiple sources and protocols to local disk.

The list of sources will be given as input in the form of urls (e.g. http://my.file.com/file, ftp://other.file.com/other, sftp://and.also.this/ending etc)

The program should download all the sources, to a configurable location (file name should be uniquely determined from the url) and then exit.

in your code, please consider:

The program should extensible to support different protocols 
some sources might very big (more than memory)
some sources might be very slow, while others might be fast
some sources might fail in the middle of download
we don't want to have partial data in the final location in any case.
