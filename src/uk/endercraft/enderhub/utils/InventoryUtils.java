package uk.endercraft.enderhub.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import uk.endercraft.endercore.EnderPlayer;
import uk.endercraft.endercore.guiapi.Gui;
import uk.endercraft.endercore.guiapi.GuiButton;
import uk.endercraft.endercore.language.Language;
import uk.endercraft.endercore.language.LanguageMain;
import uk.endercraft.endercore.managers.BungeeManager;
import uk.endercraft.endercore.managers.PlayerManager;
import uk.endercraft.endercore.utils.CustomStack;
import uk.endercraft.endercore.utils.ItemStackUtils;
import uk.endercraft.enderhub.EnderHub;
import uk.endercraft.enderhub.config.ServersConfig;
import uk.endercraft.enderhub.config.ServersConfig.Server;
import uk.endercraft.enderhub.config.ServersConfig.ServerStatusType;
import uk.endercraft.enderhub.particles.Particle;
import uk.endercraft.enderhub.particles.ParticlesManager;

public class InventoryUtils {

	public void openGameSelector(Player p) {
		Gui gui = new Gui(3, LanguageMain.get(p, "gui.gameselector.name"));
		addMinigameButton(gui, 1, Material.EYE_OF_ENDER, "endwars", p);
		addMinigameButton(gui, 4, Material.DIAMOND_SWORD, "endersg", p);
		addMinigameButton(gui, 7, Material.BOAT, "endercade", p);
		addCloseButton(gui, 22, p);
		gui.open(p);
	}

	public void openProfileGUI(Player p) {
		Gui gui = new Gui(3, LanguageMain.get(p, "gui.profilemenu.name", p.getName()));
		gui.setButton(1,
				new GuiButton(new CustomStack().setMaterial(Material.BOOK_AND_QUILL)
						.setDisplayName(LanguageMain.get(p, "gui.profilemenu.mystats.name")).build())
								.setListener(event -> {
									event.getWhoClicked().closeInventory();
									EnderHub.get().getInventoryUtils().openStatsGUI((Player) event.getWhoClicked());
								}));

		gui.setButton(4,
				new GuiButton(new CustomStack().setMaterial(Material.REDSTONE_COMPARATOR)
						.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.name")).build())
								.setListener(event -> {
									event.getWhoClicked().closeInventory();
									EnderHub.get().getInventoryUtils().openHubSettingsGUI(p);
								}));

		gui.setButton(7,
				new GuiButton(new CustomStack().setMaterial(Material.SKULL_ITEM).setDurability((byte) 3)
						.setDisplayName(LanguageMain.get(p, "gui.profilemenu.friends.name")).build())
								.setListener(event -> {
									event.getWhoClicked().closeInventory();
								}));
		addCloseButton(gui, 22, p);
		gui.open(p);
	}

	public void openHubSettingsGUI(Player p) {
		Gui gui = new Gui(3, LanguageMain.get(p, "gui.profilemenu.hubsettings.name") + " §8[§5" + p.getName() + "§8]");

		gui.setButton(1, new GuiButton(new CustomStack().setMaterial(Material.EYE_OF_ENDER)
				.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.toggleplayers")).build()));

		gui.setButton(4, new GuiButton(new CustomStack().setMaterial(Material.SIGN)
				.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.setlanguage")).build()));

		gui.setButton(7, new GuiButton(new CustomStack().setMaterial(Material.ELYTRA)
				.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.incognito")).build()));

		if (PlayerManager.getData(p).getMeta("toggleplayers") != null
				&& (boolean) PlayerManager.getData(p).getMeta("toggleplayers")) {
			gui.setButton(10,
					new GuiButton(new CustomStack().setMaterial(Material.INK_SACK).setDurability((short) 10)
							.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.enabled")).build())
									.setListener(event -> {
										PlayerManager.getData(p).setMeta("toggleplayers", false);
										p.closeInventory();
										openHubSettingsGUI(p);
									}));
		} else {
			gui.setButton(10,
					new GuiButton(new CustomStack().setMaterial(Material.INK_SACK).setDurability((short) 8)
							.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.disabled")).build())
									.setListener(event -> {
										PlayerManager.getData(p).setMeta("toggleplayers", true);
										p.closeInventory();
										openHubSettingsGUI(p);
									}));
		}

		gui.setButton(13,
				new GuiButton(new CustomStack().setMaterial(Material.BANNER).setPreviousMeta(LanguageMain.getFlag(p))
						.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.setlanguage")).build())
								.setListener(event -> {
									openLangInv(p);
								}));

		if (PlayerManager.getData(p).getMeta("incognito") != null
				&& (boolean) PlayerManager.getData(p).getMeta("incognito")) {
			gui.setButton(16,
					new GuiButton(new CustomStack().setMaterial(Material.INK_SACK).setDurability((short) 10)
							.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.enabled")).build())
									.setListener(event -> {
										PlayerManager.getData(p).setMeta("incognito", false);
										p.closeInventory();
										openHubSettingsGUI(p);
									}));
		} else {
			gui.setButton(16,
					new GuiButton(new CustomStack().setMaterial(Material.INK_SACK).setDurability((short) 8)
							.setDisplayName(LanguageMain.get(p, "gui.profilemenu.hubsettings.disabled")).build())
									.setListener(event -> {
										PlayerManager.getData(p).setMeta("incognito", true);
										p.closeInventory();
										openHubSettingsGUI(p);
									}));
		}

		gui.setButton(26, new GuiButton(
				(new CustomStack()).setMaterial(Material.SIGN).setDisplayName(LanguageMain.get(p, "gui.back")).build())
						.setListener(event -> {
							p.closeInventory();
							openProfileGUI(p);
						}));

		gui.open(p);
	}

	public void openStatsGUI(Player p) {
		Gui gui = new Gui(3, LanguageMain.get(p, "gui.profilemenu.mystats.name") + " §8[§5" + p.getName() + "§8]");

		addStatsButton(gui, 1, Material.EYE_OF_ENDER, "endwars", p);
		addStatsButton(gui, 4, Material.DIAMOND_SWORD, "endersg", p);

		gui.setButton(7, new GuiButton(new CustomStack().setMaterial(Material.BOAT)
				.setDisplayName(LanguageMain.get(p, "gui.profilemenu.mystats.endercade")).setLore(new String[] {

						"", "§c§lDownloading... §c>> §f69%",

				}).build()));

		gui.setButton(26, new GuiButton(
				(new CustomStack()).setMaterial(Material.SIGN).setDisplayName(LanguageMain.get(p, "gui.back")).build())
						.setListener(event -> {
							p.closeInventory();
							openProfileGUI(p);
						}));
		gui.open(p);
	}

	private void addStatsButton(Gui gui, int position, Material displayItem, String minigameName, Player p) {
		EnderPlayer ep = PlayerManager.getData(p);
		gui.setButton(position,
				new GuiButton((new CustomStack()).setMaterial(displayItem).setAmount(1)
						.setDisplayName(LanguageMain.get(p, "gui.profilemenu.mystats." + minigameName))
						.setLore(LanguageMain
								.get(ep, "gui.profilemenu.mystats.kills", ep.getMinigameData(minigameName).getKills()),
								LanguageMain.get(ep, "gui.profilemenu.mystats.deaths",
										ep.getMinigameData(minigameName).getDeaths()),
								LanguageMain.get(ep, "gui.profilemenu.mystats.wins",
										ep.getMinigameData(minigameName).getWins()),
								LanguageMain.get(ep,
										"gui.profilemenu.mystats.losses",
										ep.getMinigameData(minigameName).getPlayed()
												- ep.getMinigameData(minigameName).getKills()),
								LanguageMain.get(ep, "gui.profilemenu.mystats.played",
										ep.getMinigameData(minigameName).getPlayed()),
								LanguageMain.get(ep, "gui.profilemenu.mystats.xp",
										ep.getMinigameData(minigameName).getXp()))
						.build()));
	}

	private void addMinigameButton(Gui gui, int position, Material displayItem, String minigameName, Player p) {
		gui.setButton(position,
				new GuiButton((new CustomStack()).setMaterial(displayItem).setAmount(1)
						.setDisplayName(LanguageMain.get(p, "gui.gameselector." + minigameName + ".item.name")).build())
								.setListener(event -> {
									p.closeInventory();
									Bukkit.getScheduler().runTaskLater(EnderHub.get(), new Runnable() {
										@Override
										public void run() {
											openMinigameSelector(p, minigameName);
										}
									}, 1L);
								}));
	}

	private void addCloseButton(Gui gui, int position, Player p) {
		gui.setButton(position, new GuiButton((new CustomStack()).setMaterial(Material.BARRIER)
				.setDisplayName(LanguageMain.get(p, "gui.close")).build()).setListener(event -> {
					p.closeInventory();
				}));
	}

	public void openMinigameSelector(Player p, String minigame) {
		Gui ew = new Gui(5, LanguageMain.get(p, "gui.gameselector." + minigame + ".name"));
		int i = 1;
		for (Server s : ServersConfig.get().getAllServer(minigame)) {
			if (i == 10)
				break;
			if (s.getStatus().getStatus() == ServerStatusType.RESTARTING)
				continue;
			int slot = i < 6 ? 10 + i : 14 + i;
			ew.setButton(slot, new GuiButton((new CustomStack())
					.setMaterial(s.getStatus().getStatus() == ServerStatusType.PLAYING ? Material.REDSTONE_BLOCK
							: Material.EMERALD_BLOCK)
					.setDisplayName(LanguageMain.get(p, "gui.gameselector.gameitem.name", s.getStatus().getMapName()))
					.setLore(new String[] {
							LanguageMain.get(
									p, "gui.gameselector.gameitem.onlinecount", s.getStatus().getOnline(),
									s.getStatus().getMax()),
							LanguageMain.get(p, "gui.gameselector.gameitem."
									+ (s.getStatus().getStatus() == ServerStatusType.PLAYING ? "spectate" : "join")) })
					.build()).setListener(event -> {
						BungeeManager.sendToServer(p, s.getBungeeName());
					}));
			i++;
		}
		ew.setButton(ew.getSize() - 1, new GuiButton(
				(new CustomStack()).setMaterial(Material.SIGN).setDisplayName(LanguageMain.get(p, "gui.back")).build())
						.setListener(event -> {
							p.closeInventory();
							Bukkit.getScheduler().runTaskLater(EnderHub.get(), new Runnable() {
								@Override
								public void run() {
									openGameSelector(p);
								}
							}, 1L);
						}));

		ew.setUpdateTimerDelay(20);
		ew.setUpdateTimerFunction(gui -> {
			for (int o : new int[] { 11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 25 }) {
				gui.removeButton(o);
			}
			int o = 1;
			for (Server s : ServersConfig.get().getAllServer(minigame)) {
				if (o == 10)
					break;
				if (s.getStatus().getStatus() == ServerStatusType.RESTARTING)
					continue;
				int slot = o < 6 ? 10 + o : 14 + o;
				ew.setButton(slot, new GuiButton((new CustomStack())
						.setMaterial(s.getStatus().getStatus() == ServerStatusType.PLAYING ? Material.REDSTONE_BLOCK
								: Material.EMERALD_BLOCK)
						.setDisplayName(
								LanguageMain.get(p, "gui.gameselector.gameitem.name", s.getStatus().getMapName()))
						.setLore(new String[] {
								LanguageMain.get(p, "gui.gameselector.gameitem.onlinecount", s.getStatus().getOnline(),
										s.getStatus().getMax()),
								LanguageMain.get(p,
										"gui.gameselector.gameitem."
												+ (s.getStatus().getStatus() == ServerStatusType.PLAYING ? "spectate"
														: "join")) })
						.build()).setListener(event -> {
							BungeeManager.sendToServer(p, s.getBungeeName());
						}));
				o++;
			}
		});
		ew.open(p);
	}

	public void openParticleSelector(Player p) {
		Gui gui = new Gui(3, LanguageMain.get(p, "gui.particleselector.name"));
		for (Particle pt : ParticlesManager.get().getAll()) {
			if (pt.canUse(p))
				gui.setButton(pt.getID(), new GuiButton(pt.getShowcaseItem(p)).setListener(event -> {
					ParticlesManager.get().setParticle(p, pt);
					p.sendMessage(LanguageMain.get(p, "particles.changed", LanguageMain.get(p, pt.getName())));
					p.closeInventory();
				}));
		}
		gui.setButton(18, new GuiButton((new CustomStack()).setMaterial(Material.BARRIER)
				.setDisplayName(LanguageMain.get(p, "particles.item.remove")).build()).setListener(event -> {
					ParticlesManager.get().removeParticle(p);
					p.sendMessage(LanguageMain.get(p, "particles.removed"));
				}));
		gui.setButton(gui.getSize() - 1, new GuiButton(
				(new CustomStack()).setMaterial(Material.SIGN).setDisplayName(LanguageMain.get(p, "gui.back")).build())
						.setListener(event -> {
							p.closeInventory();
							Bukkit.getScheduler().runTaskLater(EnderHub.get(), new Runnable() {
								@Override
								public void run() {
									openCosmeticsGUI(p);
								}
							}, 1L);
						}));
		gui.open(p);
	}

	public void openCosmeticsGUI(Player p) {
		Gui gui = new Gui(6, LanguageMain.get(p, "gui.cosmeticselector.name"));
		gui.setButton(10, new GuiButton(
				(new CustomStack()).setMaterial(Material.MAGMA_CREAM).setDisplayName("§e§lAvailable Particles").build())
						.setListener(event -> {
							p.closeInventory();
							EnderHub.get().getInventoryUtils().openParticleSelector(p);
							// p.sendMessage("");
						}));

		ItemStack dragonHead = (new CustomStack()).setMaterial(Material.SKULL_ITEM)
				.setDisplayName("§c§lAvailable Hats §4[§cComing Soon§4]").setDurability((short) 5).build();

		gui.setButton(13,

				new GuiButton(dragonHead).setListener(event -> {
					p.closeInventory();
					// EnderHub.get().getInventoryUtils().openHatSelector(p);
					p.sendMessage(LanguageMain.get(p, "comingsoon"));
				}));

		gui.setButton(16, new GuiButton(
				(new CustomStack()).setMaterial(Material.BONE).setDisplayName("§d§lPets §5[§dComing Soon§5]").build())
						.setListener(event -> {
							p.closeInventory();
							p.sendMessage(LanguageMain.get(p, "comingsoon"));
						}));

		addCloseButton(gui, 40, p);
		gui.open(p);
	}

	public void openCosmeticsStore(Player p) {
		Gui gui = new Gui(6, LanguageMain.get(p, "gui.cosmeticstore.name"));
		gui.setButton(10,
				new GuiButton((new CustomStack()).setMaterial(Material.MAGMA_CREAM)
						.setDisplayName(LanguageMain.get(p, "gui.cosmeticstore.particlestore.name")).build())
								.setListener(event -> {
									p.closeInventory();
									EnderHub.get().getInventoryUtils().openParticleStore(p);
									// p.sendMessage("");
								}));

		ItemStack dragonHead = (new CustomStack()).setMaterial(Material.SKULL_ITEM)
				.setDisplayName(LanguageMain.get(p, "gui.cosmeticstore.hatstore.name")).setDurability((short) 5)
				.build();

		gui.setButton(13,

				new GuiButton(dragonHead).setListener(event -> {
					p.closeInventory();
					// EnderHub.get().getInventoryUtils().openHatSelector(p);
					p.sendMessage("§d§lENDERCRAFT  §dComing Soon!");
				}));

		gui.setButton(16,
				new GuiButton((new CustomStack()).setMaterial(Material.BONE)
						.setDisplayName(LanguageMain.get(p, "gui.cosmeticstore.petstore.name")).build())
								.setListener(event -> {
									p.closeInventory();
									p.sendMessage("§d§lENDERCRAFT  §dComing Soon!");
								}));

		addCloseButton(gui, 40, p);
		gui.open(p);
	}

	public void openParticleStore(Player p) {
		EnderPlayer ep = PlayerManager.getData(p);
		Gui gui = new Gui(3, LanguageMain.get(ep, "gui.particlestore.name"));
		for (Particle pt : ParticlesManager.get().getAll()) {
			if (pt.getPrice() >= 0)
				gui.setButton(pt.getID(),
						new GuiButton(ItemStackUtils.setLore(pt.getShowcaseItem(p),
								pt.canUse(p) ? LanguageMain.get(ep, "shop.owned")
										: LanguageMain.get(p, "shop.price.tokens", pt.getPrice())))
												.setListener(event -> {
													if (pt.canUse(p)) {
														ep.sendMessage("particles.alreadybought");
													} else if (ep.getTokens() >= pt.getPrice()) {
														ep.removeTokens(pt.getPrice());
														ep.addPermission(pt.getName());
														ep.sendMessage("shop.bought",
																ChatColor.DARK_PURPLE
																		+ LanguageMain.get(ep, pt.getName())
																		+ ChatColor.LIGHT_PURPLE + " particle");
													} else {
														ep.sendMessage("shop.notokens");
													}
													p.closeInventory();
												}));
		}
		gui.setButton(gui.getSize() - 1, new GuiButton(
				(new CustomStack()).setMaterial(Material.SIGN).setDisplayName(LanguageMain.get(ep, "gui.back")).build())
						.setListener(event -> {
							p.closeInventory();
							Bukkit.getScheduler().runTaskLater(EnderHub.get(), new Runnable() {
								@Override
								public void run() {
									openCosmeticsStore(p);
								}
							}, 1L);
						}));
		gui.setButton(22, new GuiButton((new CustomStack()).setMaterial(Material.NETHER_STAR)
				.setDisplayName(LanguageMain.get(ep, "shop.info.tokens", ep.getTokens())).build()));
		gui.open(p);
	}

	public void openLangInv(Player p) {
		Gui gui = new Gui(getSlots(LanguageMain.getAllLangs().size()),
				LanguageMain.get(p, "gui.profilemenu.hubsettings.setlanguage"));
		for (Language bm : LanguageMain.getAllLangs()) {
			gui.addButton(new GuiButton(bm.getStack()).setListener(e -> {
				PlayerManager.getData(p).setLang(bm.getName());
				p.sendMessage(ChatColor.GREEN + "Language changed to: "
						+ ChatColor.stripColor(bm.getStack().getItemMeta().getDisplayName()));
				p.closeInventory();
			}));
		}
		gui.open(p);
	}

	private static int getSlots(int i) {
		int actual = 1;
		while (i > 9) {
			i = i - 9;
			actual++;
		}
		return actual;
	}

}
