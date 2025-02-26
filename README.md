# HarvestHelper

A Minecraft Spigot plugin that allows players to harvest and replant crops in one action by right-clicking.

## Features

* 🌾 Right-click to harvest and instantly replant crops
* 🛠️ Works with all vanilla farmable crops (wheat, carrots, potatoes, beetroot, nether wart, cocoa beans, sweet berries)
* 🔧 Configurable tool requirements (hoe only, any tool, or bare hands)
* 💫 Optional particle effects on successful harvest
* 🔊 Optional sound effects on harvest
* ⚙️ Configurable permissions
* 📊 Optional statistics tracking
* 💼 Compatibility with protection plugins (respects block break permissions)

## Installation

1. Download the latest release from the [releases page](https://github.com/yourusername/HarvestHelper/releases)
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Configure the plugin in the `config.yml` file

## Configuration

The plugin's configuration file (`config.yml`) allows you to customize various aspects of the plugin:

```yaml
# Enable or disable the plugin
enabled: true

# Tool requirements
tool-requirements:
  enabled: true
  require-hoe: false  # If true, only hoes will work for harvest-replant

# Effects
effects:
  particles: true
  sounds: true

# Permissions
require-permission: false
permission-node: "harvesthelper.use"

# Statistics
track-statistics: true

# Debug mode (prints additional information to console)
debug: false
```

## Commands

* `/harvesthelper toggle` - Enable or disable the plugin
* `/harvesthelper reload` - Reload the configuration
* `/harvesthelper stats` - View your harvest statistics
* `/harvesthelper stats server` - View server-wide statistics (admin only)

## Permissions

* `harvesthelper.use` - Ability to use the harvest-replant feature (default: true)
* `harvesthelper.admin` - Access to admin commands (default: op)

## Supported Crops

* Wheat
* Carrots
* Potatoes
* Beetroot
* Nether Wart
* Cocoa Beans
* Sweet Berries

## How It Works

1. Right-click on a fully grown crop with the appropriate tool (if tool requirements are enabled)
2. The crop will be harvested and automatically replanted
3. The drops will be added to your inventory (or dropped if your inventory is full)
4. Optional particle and sound effects will play
5. Statistics will be tracked if enabled

## Requirements

- Spigot/Paper 1.21.4
- Java 17+

## Development
To build the plugin yourself:

1. Clone the repository
2. Run `mvn clean package`
3. Find the built jar in the `target` folder


## Support
If you find this plugin helpful, consider [buying me a coffee](https://www.paypal.com/paypalme/mckenzio) ☕

## License

[MIT License](LICENSE)

Made with ❤️ by [McKenzieJDan](https://github.com/McKenzieJDan)