## User types `make :distance 50 fd :distance` and sees turtle move forward 50: 
* User presses "run"
* `Parser` constructs appropriate `Token` subclass for each string
* Compiler creates a `Function` with all tokens from `Parser`. 
* Compiler calls `run()` on the function
* `Function` sees first token is the `make` token, of type `MakeCommand`, which is `SLogoRunnable` and expects 2 parameters. 
* `Function` calls the recursive `run()` method on it, which eventually passes it `:distance` as a `Variable` token and `50` as a `Constant` token. The command then runs. 
* The `MakeCommand` creates a `WorkspaceEntry` object with name "distance" and calls `Workspace.add()`. The command returns. 
* `Function` moves onto `fd`, which it recognizes as a `MoveForwardCommand`, and calls `run()` on it, which eventually passes it the `Variable` with name "distance". 
* `MoveForwardCommand` calls `Workspace.search()` with the variable name "distance", and finds the variable in the workspace. It adds it as its own parameter. 
* `MoveForwardCommand` runs, calling `slogo.Turtle.move()`. 
* `slogo.Turtle` updates the view to reflect the change. 