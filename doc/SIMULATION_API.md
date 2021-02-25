# API Lab Discussion
# Cell Society API Discussion

## Names and NetIDs
Kenneth Moore III (km460)
Patrick Liu (pyl8)
Yi Chen (yc311)
Liam Idrovo (lai3)


### MODULE_NAME API Motivation/Analogies
`Model`, `View`, `Controller`
#### External
* `Model`: Handles updating cell states based on simulation rules
* `View`: Displays contents to the viewer
#### Internal
* `Controller`: handles dataflow between `Model` and `View`, not exposed to external clients.



### MODULE_NAME API Classes/Methods


#### External
Adding new simulations:


- `CellularAutomatonRule`: extend into a new subclass, override advanceCellState() and setGameSpecifics() for the simulation algorithm
- `CellState`: extend into a new subclass, specify possible states in an enum
- `makeAlert()` in `CellularAutomatonView`: allows programmers in other aspects of the project (esp. the controller) to handle an error by displaying a message to the user

#### Internal

- `CellularAutomatonView`
    - `LoadXMLFile()`: handled by the View, other programmers in the project should not need this function
    - `initialize()`
