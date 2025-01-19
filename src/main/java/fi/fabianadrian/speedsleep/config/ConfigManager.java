package fi.fabianadrian.speedsleep.config;

import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;

public final class ConfigManager {
	private final ConfigLoader<MainConfig> mainConfigLoader;
	private MainConfig mainConfig;

	public ConfigManager(Path configPath) {
		this.mainConfigLoader = new ConfigLoader<>(
				MainConfig.class,
				configPath.resolve("config.yml"),
				options -> options.header("SpeedSleep Main Configuration")
		);
	}

	public void reload() throws ConfigurateException {
		this.mainConfig = this.mainConfigLoader.load();
		this.mainConfigLoader.save(this.mainConfig);
	}

	public MainConfig mainConfig() {
		if (this.mainConfig == null) {
			throw new IllegalStateException("Main configuration isn't loaded");
		}
		return this.mainConfig;
	}
}
