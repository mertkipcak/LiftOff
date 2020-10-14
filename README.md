# Liftoff (will probably change the name later)

## Game description

This project will be a game where the player controls a rocket ship that
they can steer left to right to avoid obstacles and make money by going up.
with the money player will be able to upgrade their fuel tank, steering,
and rocket power. This app will be used by anyone who might be bored.
I am doing this project as I enjoy playing casual games that include 
progress like upgrades. 

Game will work like this. Player will start the game with no upgrades.
They will launch their rocket, and after their fuel reaches 0, they will be
brought to the upgrade shop. After the player is done buying upgrades, they
move on to the next day and launch their rocket again. goal is to reach the space in
minimum number of days.

I may add a theme and visual later on as I progress through the project.

## Note for the first phase

First phase of the game will be designed as a console game where player tries to find an 
x value for the rocket where the rocket will not hit any obstacles (obstacles will have a x value 
and side length that the player needs to dodge. I did this as it is impossible to 
implement the final real time 2D game to a console interface. However, all the "User Stories"
are implemented for LiftOffConsoleGame. LiftOffGame and its tests have been completely commented out
as they will not be used until further phases. Tests for non-console game are also not
completed yet as I need to learn more about the java coordinate system.

**Also in this version of the game width of the rocket is just 1**

## User Stories

- As a user, I would like to be able to add multiple Obstacles to a LiftOffGame.
- As a user, I would like to be able to buy upgrades.
- As a user, I would like to be able to control my rockets x value.
- As a user, I would like to be able to reset the game.
- As a user, I would like to be able to lose a life when I hit obstacles 
- As a user, I would like to be able to finish the day when my life reaches 0.

