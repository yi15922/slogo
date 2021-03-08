## User types `50 fd 50` and sees an error message in the console

* User presses run
* `Parser` constructs appropriate `Token` subclass for each string
* Compiler creates a `Function` with all tokens from `Parser`.
* Compiler calls `run()` on the function
* `Function` first encounters the `50` token, which is of type `Constant`. It is not of type `SLogoRunnable` so `run()` cannot be called on it. 
* `Function` throws an exception, which is caught by the `Compiler`. 
* `Compiler` communicates to the `LogViewController` to print a message saying "Unexpected token: `50 <---- fd 50`". 