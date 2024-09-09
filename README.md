## Author: Harsh Patel, Siddharth Goyal

## Refactoring and Smells

## Implementation Smells
For Implementation Smells, there were no issues found in my main code. However, some magic number smells were present in test files. It's worth noting that these magic numbers were deliberately used for assigning IDs to various elements within the test classes. Since they serve a specific purpose in the test environment and do not affect the main code's quality, there's no need to remove them.

## Design Smells
In terms of Design Smells, I encountered a couple of issues in my code related to the way I was using certain features. Specifically, these issues were related to the model classes (User, Task, Board, and WorkSpace) and the UserController class.

Firstly, I observed two types of smells called "Unnecessary Abstraction" and "Broken Modularization" in the model classes. These smells were triggered by the way I was using the Lombok features to automatically generate getters, setters, and constructors for these classes. From my point of view, it would be okay if we didn't refactor that smell because I know that setters, getters, and constructors are important and necessary parts of code. But as DesignateJava was showing a smell, to refactor it, I removed the lambok and manually wrote the getters, setters, and constructors. And hence the code smell was removed.

The second issue was with a smell called "Feature Envy" in the UserController class. This smell indicated that the "reset password" method I had implemented in the UserController class should actually belong in the UserService class, as it was more relevant and fitting there. Following the best practice, I moved this method to the UserService class, and this action effectively resolved the code smell.

Furthermore, there was an Smell with the code in the UserTest file related to insufficient modularization. It was mentioned that there were numerous public methods, but I chose not to eliminate that concern. The reason behind this decision is that all these public methods were designed to check whether other methods were functioning correctly or not, and they served this specific purpose.

Lastly, there was a remaining Design Code Smell known as "Unutilized Abstraction," which pointed out that some Controller classes were seemingly unused. However, I disagreed with the need to remove them because Controller classes serve an important purpose. They are responsible for handling API requests from the frontend, and they play a crucial role in the application's architecture.  Thus, I decided not to make any changes to the Controller classes, as I believe they are essential components of the codebase and should not be removed.

## Architecture Smells
The components that show this smells have been intentionally designed to fulfill specific functional requirements and work cohesively to handle user-related operations efficiently. As these components play a critical role in the system and are optimized for their purpose, addressing the "Scattered Functionality" smell might disrupt their seamless integration and introduce potential risks. Therefore, considering the current context and priorities, it is best to retain the existing design while monitoring the codebase for any future improvements.

## Test Smells
The detected smells in this test method, namely the absence of any assertions and the test being empty, both arise from the same reason which is it lacks any test logic or assertions. Since the test was automatically generated and not intentionally created, it may not be applicable to include assertions for every scenario. Therefore, enforcing assertions in this particular test could be unnecessary as it might not have a meaningful purpose for the application's functionality. Considering its automated nature, it is reasonable to leave it as is.

By addressing the mentioned Design Smells, Implementation Smells, Architecure Smells Test Smells and making the appropriate adjustments, I have enhanced the overall quality of the code and ensured that it adheres to better design principles.
