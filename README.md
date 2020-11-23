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

Json implementation have been heavily inspired and copied from JsonSerializationDemo.

## Phase 4: Task 2

Added type hierarchy for control related classes where both ExactControl and SmoothControl
extends Control and they implement rightAction and leftAction differently.

## User Stories

- As a user, I would like to be able to add multiple Obstacles to a LiftOffGame.
- As a user, I would like to be able to buy upgrades.
- As a user, I would like to be able to control my rockets x value.
- As a user, I would like to be able to reset the game.
- As a user, I would like to be able to lose a life when I hit obstacles.
- As a user, I would like to be able to finish the day when my life reaches 0.
- As a user, I would like to be able to save my progress in the game.
- As a user, when I start the application, I want to be given the opportunity 
to load my progress or start a new game.

## Phase 4: Task 3

For now, I don't see any coupling in the design diagram. If I was to improve
on this project I would change fields to use the observer pattern.

