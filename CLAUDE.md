# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an FTC (FIRST Tech Challenge) robot codebase for the 2024-2025 "Into The Deep" game, forked from [acmerobotics/road-runner-quickstart](https://github.com/acmerobotics/road-runner-quickstart). It uses Road Runner v1.0 for advanced motion control and path planning.

The repository builds upon the Road Runner quickstart template with custom implementations for the "Into The Deep" game mechanics.

## Key Architecture

### Road Runner Integration
- **MecanumDrive**: Main drive class extending Road Runner's MecanumDrive with custom localization
- **TankDrive**: Alternative tank drive implementation 
- **TwoDeadWheelLocalizer**: Primary localization using two dead wheels + IMU
- **ThreeDeadWheelLocalizer**: Alternative localization with three dead wheels

### Game-Specific Mechanisms
Located in `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Components/`:
- **SpecimenTool**: Manages specimen manipulation with servo control
- **Arm**: Controls arm rotation with precise encoder positioning  
- **Slides**: Linear slide extension system with touch sensor reset
- **SampleHolder**: Manages sample intake and holding

### OpMode Structure
- **BlueSide2Plus0**: Main autonomous routine for blue alliance
- **BlueLeftSideAuto**: Alternative blue side autonomous
- **IntoTheDeepCoolTeleOp**: Primary driver-controlled OpMode
- **IntoTheDeepTestTeleOp**: Testing TeleOp for mechanism validation

## Build Commands

```bash
# Build the project
./gradlew build

# Clean and build
./gradlew clean build

# Install on connected robot controller
./gradlew installDebug

# Build release APK
./gradlew assembleRelease
```

## Development Workflow

1. **Hardware Configuration**: Update hardware maps in OpModes to match robot configuration
2. **Road Runner Tuning**: Follow the tuning sequence:
   - DeadWheelDirectionDebugger
   - AngularRampLogger  
   - ForwardPushTest
   - ForwardRampLogger
   - LateralRampLogger
   - ManualFeedforwardTuner
   - BackAndForth

3. **Autonomous Development**: Use Road Runner's trajectory builder for path planning
4. **Testing**: Use IntoTheDeepTestTeleOp for mechanism validation

## Key Technologies

- Android SDK 34
- Gradle 8.1.4
- Road Runner Core 1.0.0
- FTC Robot Controller SDK 10.1
- Apache Commons Math 3.6.1

## Code Examples

### Creating Autonomous Paths
```java
drive.actionBuilder(beginPose)
    .setTangent(Math.toRadians(270))
    .splineToLinearHeading(new Pose2d(3.5, -30, Math.toRadians(270)), Math.toRadians(270))
    .build()
```

### Parallel Actions
```java
Actions.runBlocking(
    new ParallelAction(
        drive.actionBuilder(currentPose).strafeToLinearHeading(new Vector2d(10, -34), Math.toRadians(315)).build(),
        armPivot.SpecimenScoreHigh(),
        slides.SlidesToSpecimenHigh()
    )
);
```

## Important Parameters

From MecanumDrive.java:
- **IN_PER_TICK**: 0.0007815567156394018 (wheel encoder calibration)
- **TRACK_WIDTH_TICKS**: 17741.49723527839 (robot width in encoder ticks)
- **Max Velocity**: 50 in/s
- **Max Acceleration**: 45 in/s²
- **Max Angular Velocity**: π rad/s

## Common Issues & Solutions

1. **Localization Drift**: Check dead wheel mounting and update IN_PER_TICK calibration
2. **Path Following Errors**: Verify feedforward gains using ManualFeedforwardTuner
3. **Hardware Not Found**: Update hardware device names in OpMode initialization

## Testing

Currently no automated tests. Testing is done through:
- Hardware validation OpModes (IntoTheDeepTestTeleOp)
- Road Runner tuning OpModes
- Manual testing on robot

## Resources

- [Road Runner v1.0 Documentation](https://rr.brott.dev/docs/v1-0/tuning/)
- FTC SDK Documentation: Available in FtcRobotController module