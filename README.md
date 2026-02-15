# AnyWhere

**AnyWhere** is a lightweight Android location spoofing tool designed for developers to debug LBS applications and users to test geolocation features.

## Main Features

*   **Modern UI Design**: Based on **Material Design 3** specifications, providing a clean, modern, and vibrant visual experience.
*   **Dark Mode**: Deep integration with the system dark theme.
*   **Precise Simulation**: Supports selecting any coordinate on the map for location spoofing.
*   **Joystick Control**: Floating joystick supporting simulated movement at different speeds (walking, running, driving).
*   **History Records**: Automatically saves historical location records; defaults to the last used location on entry.
*   **IP Geolocation**: Supports obtaining coordinates from IP addresses for quick input, simplifying coordinate entry.
*   **Real Orientation**: Syncs with the phone's physical orientation, making the simulated location direction change in real-time as the phone rotates.
*   **Mock Hiding**: Integrated LSPosed module functionality to hide mock location markers, bypassing most apps' mock location detection.

## Preview

|           Welcome Page            |       Map Home        |       LSPosed Module Settings        |
| :---------------------------: | :-------------------: | :---------------------------: |
| ![Welcome](docs/welcome.webp) | ![Map](docs/map.webp) | ![LSPosed](docs/lsposed.webp) |

## Important Instructions Before Use

To ensure the app runs properly, please follow these steps:

1.  **Enable Developer Mode**: Go to system settings and enable "Developer Options".
2.  **Set Mock Location App**: Enter "Developer Options" -> "Select mock location app", and select **AnyWhere** from the list.
3.  **Grant Permissions**: On first launch, please grant the necessary location and overlay permissions.
4. **LSPosed (Optional)**: Enable the module, check "System Framework" and "your target app" to prevent mock location detection.

## Disclaimer

**Please read the following terms carefully. By downloading, installing, or using this software, you acknowledge that you have read and agreed to this disclaimer:**

1.  **Usage Restrictions**: This software is for **software development debugging**, **functionality testing**, and **personal learning and research** purposes only. It is **strictly prohibited** to use this software for any behavior that violates laws and regulations, infringes on others' rights, or violates third-party platform rules (including but not limited to fake attendance check-ins, game cheating, online fraud, etc.).
2.  **At Your Own Risk**: Any consequences arising from users' improper use of this software (including but not limited to account bans, service interruptions, data loss, legal disputes, etc.) shall be **borne by the user**. The developer assumes no direct or indirect legal responsibility or joint liability for users' usage behavior.
3.  **No Warranty**: This software is provided "as is" without any express or implied warranties. The developer does not guarantee the absolute stability, compatibility, or accuracy of the software on all devices or system versions.
4.  **Privacy Protection**: This software promises not to collect, store, or upload any user's personal privacy data or real location information.

## Acknowledgments

This project is based on the open-source project [GoGoGo](https://github.com/ZCShou/GoGoGo) and has been further developed.

We have conducted deep streamlining and refactoring based on the original project:
- Removed Baidu's proprietary map SDK and fully replaced it with the open-source **OpenStreetMap**.
- Streamlined redundant UI and features, improving the app's startup speed and operational efficiency.
- Rewrote parts of the core logic to adapt to new architectural requirements.

We extend our sincere gratitude to the original author [ZCShou](https://github.com/ZCShou) and contributors for their open-source spirit and contributions to the community.

## License

This project follows the **GNU General Public License v3.0 (GPL-3.0)**.

*   **AnyWhere** © 2026 Cxorz.
*   Based on **GoGoGo** © ZCShou.

You are free to copy, modify, and distribute this project, but you must comply with the GPL v3.0 license provisions (including keeping it open source, retaining original author copyright notices, etc.). For detailed terms, please refer to the [LICENSE](LICENSE) file in the project root directory.

---
*This project is for technical exchange and learning purposes only. Please do not use it for illegal purposes.*
