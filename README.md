#### How to generate random int file
```bash
./gradlew randomFile
```

#### How to decode random file to readable format
```bash
./gradlew decodeRandomFile
```

#### How to run app
```bash
./gradlew sortIntFile
```

#### How to decode result file
```bash
./gradlew decodeResultFile
```

#### How to check result :)
```bash
head -n 100 testRun/intFileSortedDecoded
tail -n 100 testRun/intFileSortedDecoded
```

#### Conclusions
* RandomAccessFile is slow.
