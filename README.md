# AnyWhere

**AnyWhere** is a lightweight Android location spoofing tool designed for developers to debug LBS applications and users to test geolocation features.

## Key Features

*   **Modern UI Design**: Based on **Material Design 3** specifications, providing a clean, modern, and vibrant visual experience.
*   **Dark Mode**: Deeply adapted to the system dark theme.
*   **Precise Simulation**: Support selecting any coordinates on the map for location spoofing.
*   **Joystick Control**: Floating joystick supporting simulated movement at different speeds such as walking, running, and driving.
*   **History Records**: Automatically saves historical location records; defaults to the last location on entry.
*   **IP Location**: Support obtaining latitude and longitude through IP address for quick coordinate input.
*   **True Orientation**: Syncs with the phone's physical orientation, making the simulated location direction change in real-time as the phone rotates.
*   **Mock Hiding**: Integrates LSPosed module functionality to hide mock location markers, bypassing most apps' mock location detection.

## Preview

|           Welcome Page            |       Map Homepage        |       LSPosed Module Settings        |
| :---------------------------: | :-------------------: | :---------------------------: |
| ![Welcome](docs/welcome.webp) | ![Map](docs/map.webp) | ![LSPosed](docs/lsposed.webp) |

## Important Instructions Before Use

To ensure the application runs properly, please follow these steps:

1.  **Enable Developer Mode**: Go to system settings and enable "Developer Options".
2.  **Set Mock Location App**: Enter "Developer Options" -> "Select mock location app", and select **AnyWhere** from the list.
3.  **Grant Permissions**: On first launch, please grant the necessary location and overlay permissions.
4. **LSPosed (Optional)**: Enable the module and check "System Framework" and "Your Target App" to prevent mock location detection

## Disclaimer

**Please read the following terms carefully. Downloading, installing, or using this software indicates that you have read and agree to this disclaimer:**

1.  **Usage Restrictions**: This software is intended for **software development and debugging**, **functional testing**, and **personal learning and research** purposes only. It is **strictly prohibited** to use this software for any activities that violate laws and regulations, infringe on the rights of others, or violate third-party platform rules (including but not limited to fake attendance check-ins, game cheating, online fraud, etc.).
2.  **Consequences at User's Own Risk**: Any consequences arising from the user's improper use of this software (including but not limited to account bans, service interruptions, data loss, legal disputes, etc.) shall be **borne by the user**. The developer bears no direct or indirect legal liability or joint liability for the user's actions.
3.  **No Warranty**: This software is provided "as is" without any express or implied warranties. The developer does not guarantee the absolute stability, compatibility, or accuracy of the software on all devices or system versions.
4.  **Privacy Protection**: This software commits to not collecting, storing, or uploading any user's personal privacy data or real location information.

## Acknowledgments

This project is based on the open-source project [GoGoGo](https://github.com/ZCShou/GoGoGo) with secondary development.

We have conducted extensive streamlining and refactoring based on the original project:
- Removed Baidu's proprietary map SDK and fully replaced it with the open-source **OpenStreetMap**.
- Streamlined redundant UI and features, improving the application's startup speed and runtime efficiency.
- Rewrote parts of the core logic to adapt to new architectural requirements.

We extend our sincere thanks to the original author [ZCShou](https://github.com/ZCShou) and contributors for their open-source spirit and contributions to the community.

## License

This project follows the **GNU General Public License v3.0 (GPL-3.0)**.

*   **AnyWhere** © 2026 Cxorz.
*   Based on **GoGoGo** © ZCShou.

You are free to copy, modify, and distribute this project, but you must comply with the GPL v3.0 license (including maintaining open source and retaining the original author's copyright notice, etc.). For detailed terms, please refer to the [LICENSE](LICENSE) file in the project root directory.

---
*This project is for technical exchange and learning purposes only. Do not use it for illegal purposes.*
