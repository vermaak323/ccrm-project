Open your zsh terminal
command: cd
then drag and drop your file so that the file path is automatically inserted
command: javac -d out $(find src -name "*.java")
command to check if it is compiled: ls out
it will show edu
command: java -up out edu.ccrm.cli.CampusApp
