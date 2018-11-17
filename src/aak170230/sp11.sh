
# make a output Directory
cd
rm -rf sp11Output/
mkdir sp11Output
cd sp11Output

#build the java file
javac -d /Users/$USER/sp11Output /Users/$USER/IdeaProjects/impl\ of\ DS/src/sp11/src/aak170230/KLargestElement.java
# javac -d /Users/$USER/sp11Output /Users/$USER/IdeaProjects/impl\ of\ DS/src/sp11/src/aak170230/BinaryHeap.java

#RUN for different sizes of input both choice 0 & 1
echo "Select for 16M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 16000000 0 25

echo "PriorityQueue for 16M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 16000000 1 25

echo "Select for 32M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 32000000 0 25

echo "PriorityQueue for 32M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 32000000 1 25

echo "Select for 64M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 64000000 0 25

echo "PriorityQueue for 64M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 64000000 1 25

echo "Select for 128M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 128000000 0 25

echo "PriorityQueue for 128M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 128000000 1 25

echo "Select for 256M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 256000000 0 25

echo "PriorityQueue for 256M input size"
java -Xss512m -Xms4g sp11/src/aak170230/KLargestElement 256000000 1 25