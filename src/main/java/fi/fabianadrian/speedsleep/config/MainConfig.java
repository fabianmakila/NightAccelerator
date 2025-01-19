package fi.fabianadrian.speedsleep.config;

import net.kyori.adventure.bossbar.BossBar;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class MainConfig {
	private List<String> disabledWorldNames = List.of();
	private TitleConfig title = new TitleConfig();
	private BossbarConfig bossbar = new BossbarConfig();
	private CurveConfig curve = new CurveConfig();

	public List<String> disabledWorldNames() {
		return this.disabledWorldNames;
	}

	public TitleConfig title() {
		return this.title;
	}

	public BossbarConfig bossbar() {
		return this.bossbar;
	}

	public CurveConfig curve() {
		return this.curve;
	}

	@ConfigSerializable
	public static class TitleConfig {
		private String title = "<time>";
		private String subtitle = "<translatable:sleep.players_sleeping:'<world_players_sleeping>':'<world_players_total>'>";

		public String title() {
			return this.title;
		}

		public String subtitle() {
			return this.subtitle;
		}
	}

	@ConfigSerializable
	public static class BossbarConfig {
		private String title = "<translatable:sleep.players_sleeping:'<world_players_sleeping>':'<world_players_total>'>";
		private BossBar.Color color = BossBar.Color.BLUE;

		public String title() {
			return this.title;
		}

		public BossBar.Color color() {
			return this.color;
		}
	}

	@ConfigSerializable
	public static class CurveConfig {
		private int min = 0;
		private int max = 20;
		private double factor = 1.0;

		public int min() {
			return this.min;
		}

		public int max() {
			return this.max;
		}

		public double factor() {
			return this.factor;
		}
	}
}
