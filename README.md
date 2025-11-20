# Vetoned

### Basic Meal Planner in Java + Java Swing
CPSC210 Term Project by Marcus Lim, using JUnit for testing

![Alt text](MealPlanImg.jpg)
## Overview - What, who, why?
This Meal Planning application will allow users to avoid the hassle of answering "What will I have for dinner?", specifically tailored to vegetarian, keto vegetarian and vegan diets. 

Users will (eventually) be able to:
- Choose recipes from a recipe browser 📋
- Add custom recipes for dishes
- Create daily & weekly meal plans ✅
- Save & retrieve daily & weekly meal plans
- Track ingredients 🥕
    - Groceries (to buy)
    - Pantry (items already stored)
    - Leftovers (meals/components left over)
- Retrieve a list of the next groceries to buy

Most meal planners are not versatile or convenient for my family, as we follow a strict vegetarian diet and eat primarily Asian cuisine in contrast to the wide applications suiting Western cuisine. Moreover, we have a history of diabetes and tend to eat lots of carbohydrates and sugars, putting us at risk of diabetes. It is a *nightmare* when trying to think of what to cook next. 

In addition, who wants to waste money on junk food and throw out leftovers that everyone forgot about? With this meal planner app, grocery lists will allow users to know exactly what they need on their grocery sprees, and track any leftover meals or dishes that need a home.

Therefore, a meal planner suited to *Asian keto vegetarian diets*, or Asian vegetarian diets would save us the hassle, headache, and hunger by allowing us to plan our meals and cook worry-free!

## Running the Project
You can download and run VeToned.jar to start the project.

## User Stories
As a user, I want to be able to:
- add recipes to my recipe collection, and specify the name, type of dish (course, side, snack), diet (vegetarian, vegan, keto, keto-vegetarian, keto-vegan, non-vegetarian), total cook time, list of ingredients and if they are starred
- view a list of recipes in my recipe collection
- select a recipe in my recipe collection to view in detail and edit the name, type of dish, diet, cook time, ingredients and starred status or remove the recipe from my recipe collection
- sort my recipe collection by name, (later: type, diet, cook time, ingredients count or if they are starred)
- get a list of all ingredients in a recipe collection (later: simplified list)

- add recipes from my recipe collection to a day plan, and specify the name, date and starred status of the day plan
- specify the time of day (Morning, Afternoon, Evening, Unspecified/Snack) of the recipe when adding to a day plan
- view a list of recipes in a day plan, organized by time of day
- select a day plan to view in detail and edit the name, date, recipes and starred status
- select a recipe in my day plan to view in detail and edit the time of day, or remove the recipe from the day plan
- get a "shopping list" of ingredients from all recipes in a day plan (later: simplified list)

- be reminded to save my meal planner (recipe browser, day plan browser) when I select the quit option from the homepage, and have the *option* to do so or not
- be given the *option* to load my meal planner (recipe browser, day plan browser) from file from the homepage


## Some additions removed for time constraints:
- add day plans to a weekly plan and specify the day of the week
- view a list of day plans in a weekly plan
- select a day plan in a weekly plan to view in detail (list of recipes) and edit the day of the week, or remove the day plan from the weekly plan
- get a simplified "shopping list" of ingredients from all recipes from all day plans in a weekly plan

- View & Edit a calendar of weekly plans that updates with today's date
- View a day plan for today's date automatically
- View & Specify recipe instructions
- Import recipes from online
- Add in estimated costs for each ingredient and meals that depends on seasonality of ingredients

- Set health & diet goals through macronutrients
- Track macronutrients (Carbs, Fats, Proteins) 💪
    - Compare macronutrients to the average/recommended amounts
- Track even more nutrients (ie. fibre, cholesterol, vitamins & minerals)

# Instructions for End User
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by pressing the "Recipe Browser" tab or on startup, then pressing "Create a Recipe" to add a new Recipe to the RecipeCollection of the Recipe Browser
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by pressing the "Recipe Browser" tab or on startup, then pressing "Sort Recipes by Name" to sort Recipes by name in the RecipeCollection of the Recipe Browser
- You can additionally view a recipe in detail by pressing the "Recipe Browser" tab or on startup, then selecting any existing recipe name that has been added to the RecipeCollection of the RecipeBrowser
- You can locate my visual component in the "Recipe Browser" tab beside the "Create a Recipe" button as a plus icon, and as part of the background in the "Save to file" and "Load from file" tabs
- You can save the state of my application by pressing the "Save to file" tab and pressing "Save to file"
- You can reload the state of my application by pressing the "Load from file" tab and pressing "Load from file"

# Phase 4: Task 2 - Example Event Log
A representative sample of the events that occur when the meal planner runs (with day plans unimplemented):

Tue Nov 26 10:24:43 PST 2024\
Loaded recipe collection from file.\
Tue Nov 26 10:24:43 PST 2024\
Added Recipe: omelette\
Tue Nov 26 10:24:43 PST 2024\
Added Recipe: Apple\
Tue Nov 26 10:25:07 PST 2024\
Added Recipe: Granola bar\
Tue Nov 26 10:25:16 PST 2024\
Added Recipe: Pizza for four\
Tue Nov 26 10:25:20 PST 2024\
Sorted Recipes by name\
Tue Nov 26 10:25:24 PST 2024\
Saved recipe collection to file.

# Phase 4: Task 3 - Improvements & Considerations
Future improvements to refactor the project and improve design include:
- Update console-based files to better use enums instead of a switch on numbers
- Abstracting some kind of Collection abstract class for RecipeCollection and DayPlanCollection, as they share very similar fields and methods. This would improve the design by reducing redundancy, as well as making it easier to add future collections, such as a WeeklyPlanCollection or Month that could contain multiple WeeklyPlans. Or, if I knew how, I could use Java's built-in Iterable interface or Collections class to reduce the redundancy of these collections.
- Abstracting an Item abstract class for Recipe and DayPlan that has a name and can be starred. This would also improve design by reducing redundancy, as both share those fields and the corresponding methods related to those fields (ie. getName(), getStarred(), setName(), setStarred())
- Introducing a RecipeCollectionSorter class that handles all the sorting of the RecipeCollection class (as I may add more comparators for sorting recipes). This improves cohesion by following the Single Responsibility Principle, allowing the RecipeCollection to represent a RecipeCollection and nothing more. Similarly, I could introduce a DayPlanCollectionSorter class that handles all the sorting of the DayPlanCollection class and extract the sorting methods out of the DayPlanCollection to improve cohesion.
- Extracting the save and load functionality out of the MealPlanner class. Once again, following the Single Responsibility Principle, I could introduce new UI classes to represent the saving and loading tabs of my MealPlanner application to improve cohesion and make it easier to understand my program just from the UML diagram.
- TRADEOFF: Combining the reader/writer for RecipeCollection and DayPlanCollection into a single reader/writer in the MealPlanner class, to store the entire state of the program into one file, rather than two. Currently, there are lots of arrows from the ui package to the persistence package; from the MealPlanner class to the JsonReader/JsonWriter class, which makes the diagram look messy. Having a single reader and writer for both RecipeCollection and DayPlanCollection could reduce coupling and simplify the UML diagram. However, it may make it harder to read and debug the saving and loading code, as the files will be saved in the same location. It is a tradeoff of reducing coupling but reducing cohesion.
- TRADEOFF: Moving RecipeDisplay to RecipeBrowser in the ui package as an inner class. This is because the RecipeDisplay displays a selected recipe, and is in the same frame as the RecipeBrowser. This could simplify the UML diagram as well, however, it also has the same issues of making it harder to debug, and possibly reducing cohesion (single-responsibility)