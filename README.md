# How to run
## Setting
  * check your computer's private IP:
   1. for Windows users: `ipconfig`
   2. for Mac/Linux users: `ifconfig`

  Normally you can find your public/private IP in the connected interface(check your wired or wireless interface).

  * go to `config.java`, modify string declaration

      > public static final String server_ip = "*your_server_ip*"

## Build
  * go to `jserver` directory, run command `make` to build our java server

## Run
  * run our server first, go to `jserver` directory and run command `make run` to start the server

  * start app and try using

---

# Help

1. finish all TODO

2. determine the data class (transfered between server and client)

3. Important !!!!!!!!!
	every time you commit and push
	you should only add the file or upper directory you modified!!!!!!!!!
