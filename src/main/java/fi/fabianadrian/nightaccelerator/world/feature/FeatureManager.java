package fi.fabianadrian.nightaccelerator.world.feature;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.BossbarSection;
import fi.fabianadrian.nightaccelerator.config.section.TitleSection;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class FeatureManager {
	private final List<Feature> features = new ArrayList<>();
	private final AccelerationFeature accelerationFeature;
	private BossbarFeature bossbarFeature;
	private TitleFeature titleFeature;

	public FeatureManager(NightAccelerator plugin, SleepWorld world) {
		this.accelerationFeature = new AccelerationFeature(plugin, world);
		this.features.add(this.accelerationFeature);

		BossbarSection bossbarSection = plugin.config().bossbar();
		if (bossbarSection.enabled()) {
			this.bossbarFeature = new BossbarFeature(plugin, world);
			this.features.add(this.bossbarFeature);
		}
		TitleSection titleSection = plugin.config().title();
		if (titleSection.enabled()) {
			this.titleFeature = new TitleFeature(plugin, world);
			this.features.add(this.titleFeature);
		}
	}

	public void start() {
		if (this.accelerationFeature.running()) {
			return;
		}
		this.features.forEach(Feature::start);
	}

	public void stop() {
		if (!this.accelerationFeature.running()) {
			return;
		}
		this.features.forEach(Feature::stop);
	}

	public void track(Player player) {
		if (this.bossbarFeature != null && this.accelerationFeature.running()) {
			this.bossbarFeature.add(player);
		}
	}

	public void untrack(Player player) {
		if (this.bossbarFeature != null) {
			this.bossbarFeature.remove(player);
		}
	}

	public void recalculate() {
		this.features.forEach(Feature::recalculate);
	}
}
