# AnyWhere

**AnyWhere** is a lightweight Android location spoofing tool designed for developers to debug LBS applications and users to test geolocation features.

## Main Features

*   **Modern UI Design**: Based on **Material Design 3** specifications, providing a clean, modern, and vibrant visual experience.
*   **Dark Mode**: Fully adapted to system dark theme.
*   **Precise Simulation**: Support for selecting any coordinates on the map for location spoofing.
*   **Joystick Control**: Floating joystick that supports simulating walking, running, driving, and other movement speeds.
*   **History Records**: Automatically saves historical location records; defaults to the last used position upon entry.
*   **IP Geolocation**: Supports obtaining latitude and longitude via IP address for quick coordinate input.
*   **Real Orientation**: Synchronizes with the phone's physical orientation, making the simulated location direction change in real-time as the phone rotates.
*   **Mock Hiding**: Integrated LSPosed module functionality to hide mock location markers, bypassing most apps' mock location detection.

## Preview

|           Welcome Page            |       Map Home        |       LSPosed Module Settings        |
| :---------------------------: | :-------------------: | :---------------------------: |
| ![Welcome](docs/welcome.webp) | ![Map](docs/map.webp) | ![LSPosed](docs/lsposed.webp) |

## Important: Read Before Use

To ensure the app runs properly, please follow these steps:

1.  **Enable Developer Mode**: Go to system settings and enable "Developer options".
2.  **Set Mock Location App**: Enter "Developer options" -> "Select mock location app", and select **AnyWhere** from the list.
3.  **Grant Permissions**: On first launch, please grant the app necessary location and overlay permissions.
4. **LSPosed (Optional)**: Enable the module, check "System Framework" and "your target app" to prevent mock location detection.

## Disclaimer

**Please read the following terms carefully. By downloading, installing, or using this software, you acknowledge that you have read and agreed to this disclaimer:**

1.  **Usage Restrictions**: This software is intended **only** for **software development debugging**, **functionality testing**, and **personal learning and research**. It is **strictly prohibited** to use this software for any activities that violate laws and regulations, infringe upon the rights of others, or violate third-party platform rules (including but not limited to false attendance check-in, game cheating, online fraud, etc.).
2.  **User Liability**: Any consequences arising from users' misuse of this software (including but not limited to account bans, service interruptions, data loss, legal disputes, etc.) shall be **borne solely by the user**. The developer assumes no direct or indirect legal liability or joint liability for users' actions.
3.  **No Warranty**: This software is provided "as is" without any express or implied warranties. The developer does not guarantee absolute stability, compatibility, or accuracy of the software on all devices or system versions.
4.  **Privacy Protection**: This software promises not to collect, store, or upload any personal privacy data or real location information of users.

## Acknowledgments

This project is based on the open-source project [GoGoGo](https://github.com/ZCShou/GoGoGo) and has been extensively refined and refactored.

We have made significant improvements based on the original project:
- Removed Baidu's proprietary map SDK and completely replaced it with open-source **OpenStreetMap**.
- Streamlined redundant UI and features, improving app startup speed and runtime efficiency.
- Rewrote parts of the core logic to adapt to new architectural requirements.

We would like to express our sincere gratitude to the original author [ZCShou](https://github.com/ZCShou) and contributors for their open-source spirit and contributions to the community.

## License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)**.

*   **AnyWhere** © 2026 Cxorz.
*   Based on **GoGoGo** © ZCShou.

You are free to copy, modify, and distribute this project, but you must comply with the GPL v3.0 license requirements (including maintaining open source and retaining original author copyright notices). For detailed terms, please refer to the [LICENSE](LICENSE) file in the project root directory.

---
*This project is for technical exchange and learning purposes only. Do not use it for illegal purposes.*
