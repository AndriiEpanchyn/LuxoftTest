Insall:
- Run MySQL server on your pc
- run migration script from file:
src/main/resources/DataBase/migration_script.sql

- connect to the date base using
login "root"
password "111111"


Usage:
Java -classpath ./bin Main  \*FilePAth*\

Search result is ArrayList of objects with line copy and line statistics:
line - copy of line, words in line are split without the delimiters.
LongestWord - longest word without delimiters.
ShortestWord - shortest word without delimiters.
LineLength - full line length including delimiters.
averageLength - average length of words in line.

All statistics is saved in database "textstatistics"
Table LineStatistics contain line statistic,
table FileStatistics contain statistic for a whole file.
Every program launch truncate the tables.