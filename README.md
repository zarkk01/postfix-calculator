# PostFixie AI: Your Fun Fact Postfix AI-Calculator

Hello there! Welcome to the world of PostFixie, a stack-based postfix calculator built with Java, Maven, and a dash of AI. PostFixie is not just any calculator, it's a calculator with a twist!

## What's the Twist?

Well, PostFixie doesn't believe in the traditional way of doing math. No sir! It follows the postfix notation, also known as Reverse Polish Notation (RPN). So instead of typing `2 + 2`, you would type `2 2 +`. It's like Yoda doing math!

But that's not all! PostFixie now comes with an AI-powered feature that provides an interesting fact about the result of your calculation. So you're not just calculating, you're learning too!

## Take a Glimpse

Here's a sneak peek of what you can expect from PostFixie AI:


![PostFixie AI after action](visuals/inAction.gif)

In the gif above, you can see (slowly) how PostFixie AI performs calculations in postfix notation and provides interesting facts about the result. Remember, PostFixie AI is not just a calculator, it's a learning tool!

## How to Use PostFixie

Before you start, you need to export your OpenAI API key (if you want to use the "Interesting fact..." thing, if not move on to the next steps). Here's how you can do it:

For Mac/Linux:

Open the terminal and type the following commands to open the shell profile file:

For bash shell:
```bash
nano ~/.bashrc
```

For zsh shell:
```bash
nano ~/.zshrc
```

Add these lines at the end of the file:
```bash
export OPENAI_API_KEY="your_api_key"
```

Replace "your_api_key" with your actual OpenAI API key. Press Ctrl + X to close the editor, followed by Y to save changes, and Enter to confirm the file name. To make these changes take effect, close and reopen your terminal.

For Windows:

Open Command Prompt as an administrator.

To set the environment variable, use the setx command followed by the variable name and its value:

```bash
  setx OPENAI_API_KEY="your_api_key"
```

Replace "your_api_key" with your actual OpenAI API key.
Close and reopen Command Prompt to make sure the changes take effect.

Then you can start using PostFixie AI:

1. Clone this repository to your local machine.
2. Navigate to the project directory.
3. Run the following command to build the project:
   ```
   mvn clean package
   ```
4. Run the following command to start the calculator:
   ```
    java -jar target/postfixie.jar
    ```
5. Start doing math, PostFixie style, and enjoy interesting facts about your results!

## Features

- Basic arithmetic operations: addition, subtraction, multiplication, and division.
- Stack-based calculation for efficient memory usage.
- Error handling for invalid inputs and mathematical errors (like division by zero).
- AI-powered feature that provides interesting facts about the result of your calculation.

## Why PostFixie?

Because who said math can't be fun? With PostFixie, you can calculate, learn, and have a laugh at the same time. So why wait? Give PostFixie a try and let the fun begin!

**Note 1:** PostFixie is not responsible for any Jedi mind tricks that may occur while using the calculator.
**Note 2:** PostFixie is a fun-loving AI, but remember, it's still an AI. Don't take everything it says as gospel. Always fact-check!
