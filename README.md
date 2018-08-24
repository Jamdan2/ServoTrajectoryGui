# ServoTrajectoryGui
A small graphic user interface for experimenting with trajectories on a servo motor.

### Installation
Right now the only way to install is to clone the project and use the [Gradle](https://gradle.org/) build tool do create a distribution.
1. At this project's [GitHub page](https://github.com/Jamdan2/ServoTrajectoryGui), press __Clone or download__ and download the zip file.
2. Extract the zip file.
3. Run the _buildAndDistribute_ task with Gradle inside this project's folder _(ServoTrajectoryGui)_.

### Hardware requirements
To use this software you need the following hardware. Please refer to the documentation on specific hardware if you have any questions.
1. [Arduino](https://www.arduino.cc/)
2. [Escon 50/5](https://www.maxonmotorusa.com/medias/sys_master/8810873815070/409510-ESCON-50-5-Hardware-Reference-En.pdf)
3. [EC-max 40](https://www.maxonmotor.com/maxon/view/product/283866)

### Software requirements
Using this program has the following software requirements. Please refer to the documentation on specific software if you have any
questions.
1. Your computer must have a [JRE](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
_(Java Runtime Enviorment)_ installed.
2. Your Arduino must be programmed to understand ALProtocol. Such a program is distributed with the [Ardulink](http://ardulink.org)
Library; upload it to your Arduino.
3. You need [Escon Studio](https://www.maxonmotorusa.com/maxon/view/content/ESCON-Detailsite) to configure your Escon 50/5.
 
### Setting Up
To get started follow the following instructions. 
1. Configure the _enable_, _controller_ and _feedback_ pins, the _input scaling_ and _output scaling_ in Escon Studio.
2. Plug the Arduino into a USB port in your computer.
3. Wire a _digital pin_ in your Arduino into the _enable_ pin in your Escon 50/5.
4. Wire a _PWM pin_ in your Arduino into the _controller_ pin in your Escon 50/5.
5. Wire a _analog input pin_ in your Arduino into the _feedback_ pin in your Escon 50/5.
6. Open the software and click the __settings__ button. Configure the pin numbers that you used on the Arduino and the scaling that
you used on the Escon 50/5.
7. You should now be able to use the software without any problems. If you have any questions, please go to this project's
[GitHub page](https://github.com/Jamdan2/ServoTrajectoryGui) and open an issue.

### Usage
1. Use the sliders to configure the trajectory on the graphs.
2. Check __use PID__ and configure _Kp_, _Ki_, and _Kd_ if you are not doing PID on the Escon 50/5.
3. Press __run__ to run the trajectory on the motor.
4. Enjoy!
