# reactivate23
Restarting FRC in 2023

The software on the Windows machine was lost, so we had to start from scratch to get a firmware image for the robotRIO and resurrect the FRC robot built by Team 426.

We have an [AM14U4 drive base](https://www.andymark.com/products/am14u-family-frame-opening-kit) - and it is a great starting point. [Swerve modules](https://www.andymark.com/products/mk4i-swerve-modules) might come later.

## Get the required programs

Following the recommendation from [WPILIB](https://docs.wpilib.org/en/stable/) we fist installed the following programs on our Windows machines:

- [LabView FRC](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/labview-setup.html#installing-labview-for-frc-labview-only) (Windows only)
- [VS Code](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html#wpilib-installation-guide)
- [FRC Driver Station Powered by NI LabVIEW](https://docs.wpilib.org/en/stable/docs/software/driverstation/driver-station.html#frc-driver-station-powered-by-ni-labview) (Windows Only)
- [FRC roboRIO Imaging Tool](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/frc-game-tools.html#installing-the-frc-game-tools) (Windows Only)
- [FRC Radio Configuration Utility](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-3/radio-programming.html#programming-your-radio) (Windows Only)
- [FRC Driver Station Log Viewer](https://docs.wpilib.org/en/stable/docs/software/driverstation/driver-station-log-viewer.html#driver-station-log-file-viewer) (Windows Only)
- [RobotBuilder](https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/robotbuilder/index.html#robotbuilder)

## Hardware design and documentation

We want to be swappable to return to PWM with a simple radio controller. And otherwise run CAN bus and control motor temperature (see [RESET 2023 event](https://github.com/ssis-robotics/reset23) )

## Software
### Creating the firmware image

This took quite some time, even on our i7

### Configuring the firmware

The motors on the CAN bus need to be addressed. But how? Can we give them individual names (configure the Talon SRX)

### Connect the Joystick controller to the Windows machine and drive the robot over Wifi

We'll see about that.




## Reactivate

Within a week we had the hardware back to a working stage like early 2020. And on the Lenovo laptop installed all needed software, trial licences and the program updated and recompiled for the 2023 libraries.

### 2023/05/30 Firmware update

First we updated the firmware of the roboRIO from 6.0.0. to 8.8.0f0. The installed program worked fine afterwards. For the update of the FRC Image with the 2023 Java Virtual Machine we had to overwrite the old image 2020_v10 with the new 2023_v3.2 and lost the installed program in the process. It has to be recompiled and uploaded again.

<img src="docs/2023-05-30_firmware.png" width="49%"> <img src="docs/2023-05-30_FRCimage.png" width="49%">

### 2023/05/29 Reactivate drive control

On Monday we installed the FRC Game Tools, connected to the **426 Wifi**, activated the sensors, reconnected all Talon SRX to the CAN bus - and with a _Logitech USB flight stick_  we were able to control the robot again! The old software on the roboRIO worked out-of-the-box!

After that we needed some software updates for the motor controllers like [Talon SRX](https://www.andymark.com/products/talon-srx-speed-controller) and [Victor SPX](https://store.ctr-electronics.com/victor-spx/). With [Phoenix Tuner X](https://pro.docs.ctr-electronics.com/en/stable/docs/tuner/index.html) this was done fast.

Then we found a Gadgeteer Pigeon IMU in our spare materials box. Connexted over the flat wire to a Talon SRX we now have a 9-DOF gyroscope with magnetometer! It was already noticed in the software.

#### Configuration details

Hidden in the code is also a [google sheet document](https://docs.google.com/spreadsheets/d/1-l5YZYubWAp52MwDntlmeQ8fC4OWeWa1os5C94XbTL8/edit?usp=sharing) to assign different configurations (**Control Systems Planning**) to team east and west. They look very similar, see here _ZombieBot West_:

| Name                    | Category  | Controlled By | Port/Address | Notes                                          |
|-------------------------|-----------|---------------|--------------|------------------------------------------------|
| LeftDriveCIM1           | Motor     | TalonCAN      |            0 |                                                |
| LeftDriveCIM2           | Motor     | TalonCAN      |            1 |                                                |
| RightDriveCIM1          | Motor     | TalonCAN      |            2 |                                                |
| RightDriveCIM2          | Motor     | TalonCAN      |            3 |                                                |
| ConveyorCIM1            | Motor     | VictorCAN     |            6 |                                                |
| ConveyorCIM2            | Motor     | VictorCAN     |            7 |                                                |
| ColorWheelDrive         | Motor     | VictorCAN     |            8 |                                                |
| ColorWheelArm           | Motor     | VictorCAN     |            9 |                                                |
| ClimbMotor 1            | Motor     | TalonCAN      |           10 |                                                |
| ClimbMotor 2            | Motor     | TalonCAN      |           11 |                                                |
| LeftEncoder             | Encoder   | TalonCAN #0   |            0 | attached to talon through SRX cable            |
| RightEncoder            | Encoder   | TalonCAN #2   |            1 | attached to talon through SRX cable            |
| ColorSensor             | I2CSensor | RoboRIO I2C   | 0x52         |                                                |
| Pigeon/navX             | IMU       | CAN           |            3 | currently attached to the rightDriveCIM2 talon |
| Pixy2                   |           | SPI           |              |                                                |
| colorWheelArmLowerLimit | DigitalIn | RoboRIO DIO   |            5 |                                                |
| colorWheelArmUpperLimit | DigitalIn | RoboRIO DIO   |            4 |                                                |

For comparison _ZombieBot East_:

| Name                 | Category  | Controlled By | Port/Address |
|----------------------|-----------|---------------|--------------|
| LeftDriveCIM1        | Motor     | TalonCAN      |            0 |
|                      |           |               |              |
| RightDriveCIM1       | Motor     | TalonCAN      |            2 |
|                      |           |               |              |
| ConveyorCIM1         | Motor     | VictorCAN     |            6 |
| ConveyorCIM2         | Motor     | VictorCAN     |            7 |
| ColorWheelDrive      | Motor     | TalonCAN      |            8 |
| ColorWheelArm        | Motor     | TalonCAN      |            9 |
| ClimbMotor 1         | Motor     | TalonCAN      |           10 |
| ClimbMotor 2         | Motor     | TalonCAN      |           11 |
| ColorSensor          | I2CSensor | RoboRIO I2C   | 0x52         |
| Pigeon/navX          | IMU       | CAN           |           15 |
| colorWheelLowerLimit | DigitalIn | RoboRIO DIO   |            0 |
| colorWheelUpperLimit | DigitalIn | RoboRIO DIO   |            1 |
|                      | DigitalIn | RoboRIO DIO   |            3 |
|                      | DigitalIn | RoboRIO DIO   |            2 |

## Driving armchair for RESET 2023

Driving armchair on the FRC 2020 drivebase for the art show "RESET" on May 25th, 2023.

### 2023/05/25 Excited visitors and users

This project was a huge success. Many people wanted to take a ride on the chair, from young to old. And they enjoyed it!

<img src="https://github.com/ssis-robotics/reset23/blob/main/docs/2023-05-25_jack.jpg" width="49%"> <img src="https://github.com/ssis-robotics/reset23/blob/main/docs/2023-05-25_nomer.jpg" width="49%">

So much that the two CIM motors actually overheated 🥵 and reduced their power. We had to make a break to let them cool a little:

![base and chair separated](https://github.com/ssis-robotics/reset23/blob/main/docs/2023-05-25_separated.jpg)

### 2023/05/15 Secure place for battery, cables and receiver

The last state of the design from March 2020 had no dedicated space for the battery, and some connections were rather loose. The MDF base plate is very thin and bends under load. A new 22mm wooden structure to hold the lead battery in place was cut, screwed and glued together. A separate power switch for the 5V power supply of the radio was installed. Now the glue needs to dry.

![driving base](https://github.com/ssis-robotics/reset23/blob/main/docs/2023-05-15_base.jpg)




## Inspiration at TAS in December 2022

The visit was great. Team 4253 would like to support us - we need to make contact! They have some vintage robots to learn from:

![pic 1](docs/2022-12-03_old_robots.jpg)

Build a robot for the previous sesion by freshmen teams to learn from working desigs (see below) and run example bots with [SDS MK4i Swerve Modules](https://www.andymark.com/products/mk4i-swerve-modules), driven by eight [Talon FX](https://store.ctr-electronics.com/falcon-500-powered-by-talon-fx/):

![pic 2](docs/2022-12-03_drivebase.jpg)
![pic 3](docs/2022-12-03_turret.jpg)
![pic 4](docs/2022-12-03_example-landscape.jpg)
![pic 5](docs/2022-12-03_example-portrait.jpg)

## FRC at the SSIS Club Expo in August 26, 2020

As noted in this [Instagram post](https://www.instagram.com/p/CEWkAkDhMu9/) the FRC robot was present with a gamepad connected to the Lenovo laptop to showcase the robot:

![frx at club expo 2020](docs/2020-08-26_club_expo.jpg)

## Start of FRC at SSIS in 2019

Evan Weinberg [@emwdx](https://github.com/emwdx) started the collaboration with 5 other schools to bring FRC to SSIS and Vietnam. The stages are documented in the repository https://github.com/ssis-robotics/Team426Robot 
