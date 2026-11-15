# AgriSense-Digital-Farming-Assistant

> **Autonomous Digital Farming Assistant & Field Telemetry OS**  
> Designed with Google Stitch â€¢ Project ID: `16831104218349931729`

---

## ðŸŒ¾ Overview

**AgriSense Telemetry OS** is a high-fidelity digital farming interface designed for corporate agriculture, precision agronomy, and autonomous field monitoring. It provides real-time vector GIS parcel tracking, Sentinel-2 multispectral NDVI canopy vigor indexing, precision variable-rate irrigation controls, and autonomous drone pest diagnostics.

---

## ðŸ“± Application Screens & Modules

| # | Screen / Module | Resolution | Key Features |
|---|---|---|---|
| **0** | **[Portal Hub (`index.html`)](index.html)** | Responsive | Master launcher & interactive gallery connecting all modules |
| **1** | **[Field & Crop Health Inspector](1_field_crop_health_inspector.html)** | 2560 Ã— 2718 | Sentinel-2 NDVI index, CWSI water stress heatmap, phenology tracker (V8 Stage), vector parcel overlays |
| **2** | **[Irrigation & Resource Management](2_irrigation_resource_management.html)** | 2560 Ã— 4308 | Center-pivot telemetry (Alpha-1, Beta-2), aquifer pressure, subsurface drip (SDI) cycle scheduling |
| **3** | **[Farm Overview & Live Telemetry](3_farm_overview_live_telemetry.html)** | 2560 Ã— 3142 | 1,420-acre Highland Ridge overview, 48/48 sensor status, weather station feed, and alert feed |
| **4** | **[Agri-AI Diagnostic & Advisory](4_agri_ai_diagnostic_advisory.html)** | 2560 Ã— 3110 | Drone anomaly scan (Aphid cluster, 78% confidence), fungal risk indexing, and agronomic AI recommendations |
| **5** | **[Dawn Machinery Showcase](5_high_tech_agricultural_field_dawn.html)** | 1376 Ã— 768 | Cinematic photorealistic asset of autonomous farming machinery, mist, and smart sensor pylons at dawn |
| **6** | **[Enterprise Login & Authentication](6_enterprise_login_authentication.html)** | 2560 Ã— 2048 | Role-based authentication (*Agronomist*, *Farm Owner*, *Hardware Tech*) with demo credential presets |

---

## ðŸš€ Running Locally

All screens are built with standard HTML5, Tailwind CSS, Google Fonts, and Material Symbols. No heavy build step is required:

### Option 1: Direct Browser Access
Double-click `index.html` to open the application in any modern web browser.

### Option 2: Local HTTP Server
```bash
# Using Python
python -m http.server 5174

# Using Node.js
npx serve .
```
Then navigate to [http://localhost:5174](http://localhost:5174).

### Option 3: Java Backend and Database

The Spring Boot application serves the screens and API from one origin. It uses a persistent H2 database by default; PostgreSQL is available with the included Docker Compose file.

```powershell
cd agrisense-backend
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080). The demo account is `marcus.vance@highlandridge.ag` with password `[set AGRISENSE_DEMO_PASSWORD]`. Demo accounts for `FARM_OWNER` and `HARDWARE_TECH` use the same password. Set `AGRISENSE_DEMO_PASSWORD` to override it.

To use PostgreSQL, start it with `docker compose up -d`, then run `mvn spring-boot:run "-Dspring-boot.run.profiles=postgres"`. Configure `DATABASE_URL`, `DATABASE_USER`, and `DATABASE_PASSWORD` for non-local environments; change the demo password before deployment.

The API is under `/api`: `/health`, `/auth/login`, `/auth/me`, `/telemetry`, `/fields`, `/irrigation/pivots`, `/irrigation/schedules`, and `/diagnostics/anomalies` and `/diagnostics/recommendations`. Data APIs require a login session. Live telemetry is available at the authenticated WebSocket endpoint `/ws/telemetry`.

---

## ðŸ› ï¸ Tech Stack

- **UI Framework:** Tailwind CSS (CDN) + Custom Material Design Tokens
- **Icons & Typography:** Google Material Symbols Outlined, Inter & JetBrains Mono
- **Visual Assets:** High-resolution orthomosaic textures & photorealistic autonomous machinery renders
- **Interactivity:** Native vanilla JavaScript with interconnected routing and quick-switch floating dock

---

## ðŸ“„ License
This project is licensed under the MIT License.

