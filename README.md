Build with "./gradlew clean build jacocoTestReport" or "/gradlew clean build"
run with "java -jar build/libs/201809-bloom-teksystems-sdet-1.0.0-???.jar"

should provide a list of command line options
-s or --state to specify the state or state abbreviation
--url to specify the URL
    url not set defaults to using canned data from in the JAR
    url = "internal" => same as not set => use from JAR
    url = "default" =>