## Writing a Socket Server - Quote Of The Day (QOTD) Server

### Build a Java server application that conforms to RFC 865

### Usage instructions:
- Navigate to root directory
- Run the commands:
  - `javac SocketServer.java`
  - `javac TCPClient.java`
  - `javac UDPClient.java`
- Open the server on port 17
  - Run `java SocketServer`
- Connecting a client
  - Connect to TCP Client
    - Run `ncat localhost 17`
  - Connect to UDP Client
    - Run `ncat -u localhost 17`
- To use, send a message