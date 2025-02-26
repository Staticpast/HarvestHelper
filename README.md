# HarvestHelper

A Minecraft Spigot plugin that allows players to harvest and replant crops in one action by right-clicking.

## Features

* 🌾 Right-click to harvest and instantly replant crops
* 🛠️ Support for multiple crop types (wheat, carrots, potatoes, beetroot, nether wart, cocoa beans, sweet berries, and more)
* 🔧 Configurable tool requirements and item drop behavior
* 💫 Optional visual and audio effects
* ⚙️ Permissions system and statistics tracking

## Installation

1. Download the latest release from the [releases page](https://github.com/McKenzieJDan/HarvestHelper/releases)
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Configure the plugin in the `config.yml` file

## Usage

Right-click on fully grown crops to harvest and automatically replant them. The drops will be added to your inventory or dropped on the ground based on your configuration.

### Commands

* `/harvesthelper toggle` - Enable or disable the plugin
* `/harvesthelper reload` - Reload the configuration
* `/harvesthelper stats` - View your harvest statistics
* `/harvesthelper stats server` - View server-wide statistics (admin only)

### Permissions

* `harvesthelper.use` - Ability to use the harvest-replant feature (default: true)
* `harvesthelper.admin` - Access to admin commands (default: op)

## Configuration

The plugin's configuration file (`config.yml`) is organized into logical sections:

```yaml
# General Settings - Enable/disable plugin and debug mode
enabled: true
debug: false

# Harvesting Settings - Tool requirements and item drop behavior
tool-requirements:
  enabled: true
  require-hoe: false
drops:
  drop-on-ground: false

# Effects - Particles and sounds
effects:
  particles: true
  sounds: true

# Permissions and Statistics
permissions:
  require-permission: false
  permission-node: "harvesthelper.use"
statistics:
  enabled: true
```

For detailed configuration options, see the comments in the generated config.yml file.

## Requirements

- Spigot/Paper 1.21.4
- Java 17+

## Support

If you find this plugin helpful, consider [buying me a coffee](https://www.paypal.com/paypalme/mckenzio) ☕

## License

[MIT License](LICENSE)

Made with ❤️ by [McKenzieJDan](https://github.com/McKenzieJDan)