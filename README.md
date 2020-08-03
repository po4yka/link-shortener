# Link Shortener

## Local Version

Link shortener server with REST API using JAX-RS and MongoDB 3.4.

### TomEE start/stop

Start:

```
sudo /usr/share/tomcat9/bin/startup.sh
```

Stop:

```
sudo /usr/share/tomcat9/bin/shutdown.sh
```

### Communication with server

To test a deployed local server, use the utility [Advanced REST Client (ARC)](https://install.advancedrestclient.com/install).

For `PUT` command:

- Add `HEADER` with name `content-type` with `text/plain` parameter;

- Write your text in `BODY`;

- Send for command by address: `http://localhost:8080/jxr-rs-1.0-SNAPSHOT/api/links`.

For `GET` command just specify the address `http://localhost:8080/jxr-rs-1.0-SNAPSHOT/api/links/NUMBER`, where 'NUMBER' - one of the returned by 'PUT' values.

## AWS Version

The same backend server based on AWS server.