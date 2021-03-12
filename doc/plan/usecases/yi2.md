## User types `to makeL [ :distance ] [ fd :distance rt 90 fd :distance ] makeL 50` and sees the turtle go forward 50 pixels, then right 50 pixels. 

* User presses run
* `Parser` constructs appropriate `Token` subclass for each string
* Compiler creates a `Function` with all tokens from `Parser`.
* Compiler calls `run()` on the function
* `Function` first encounters the `to` token, which is of type `MakeFunctionCommand`. It calls `run()` on that command, and eventually passes it the name of the function (generic `Token`), and 2 `List` tokens. 
* `MakeFunctionCommand` runs, creating a `Function` called `makeL`. The new `makeL` function contains all `Token`s inside the 2 `List`s. The `makeL` function is added as a `WorkspaceEntry` to the `Workspace`. 
* `MakeFunctionCommand` returns successful. 
* `Function` continues through its `Token`s, now encountering `makeL`, which is of type generic `Token`. 
* `Function` calls `Workspace.search()` on the token's string value. 
* `Workspace` returns a `WorkspaceEntry` of type `Function`. 
* `Function` calls `run()` on the `makeL` function and eventually passes it the `Constant` 50. 
* Once 50 is passed to `makeL`, it creates a new `WorkspaceEntry` object with the `:distance` variable passed to it earlier and the 50. 
* `makeL` then runs, which recursively calls `run()` on all its inner methods, searching the `Workspace` for the value of `:distance` when needed. 
* `Command`s inside `makeL` update the `slogo.Turtle`'s location. 