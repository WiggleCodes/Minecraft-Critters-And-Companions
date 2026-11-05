<p align="center">
  <img src="https://i.imgur.com/THxDIWa.png" alt="Title"/>
  <img src="https://i.imgur.com/s2AsOQd.png" alt="Display"/>
  <br><br>
  <a href="https://modrinth.com/mod/critters-and-companions"><img alt="modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg"></a>
  &nbsp
  <a href="https://www.curseforge.com/minecraft/mc-mods/critters-and-companions"><img alt="curseforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg"></a>
  <br><br>
  <b>Critters and Companions was created by <a href="https://github.com/bonsaistudi0s">Bonsai Studios</a></b>
  <br>
  Original developers: <b>Joosh</b> &amp; <b>EterDelta</b>
  <br>
  <a href="https://github.com/bonsaistudi0s/CrittersAndCompanions">Original repository</a>
  &nbsp;·&nbsp;
  <a href="https://discord.gg/bonsai-studios-675743114173874176">Bonsai Studios Discord</a>
</p>

## About

Critters and Companions is a vanilla-style creature mod that will fill the overworld with new animals!

## About This Version

> [!NOTE]
> **This is my own unofficial re-upload of Critters and Companions.** It's not an official release and isn't affiliated with or endorsed by Bonsai Studios.

This version has the latest patches in place so it works with the **latest version of Minecraft (26.3)** on both Fabric and NeoForge.

I only did this so my girlfriend could play this mod on an up-to-date version of Minecraft alongside the other mods she uses. She's very grateful to the original developers for creating it, and I'd like to say a personal thank you as well. All credit for the mod goes to Bonsai Studios and its original creators.

If an official update for 26.3 is released, please use that instead and support the original developers through the links above.

## Changes in This Version

- Ported to **Minecraft 26.3** for both Fabric and NeoForge
- Updated dependencies: GeckoLib 5.5.7, Architectury API 22.0.5, Fabric API 0.165.0+26.3, NeoForge 26.3.0.99, Curios 17.0.0-beta.3+26.3, YACL 3.9.7, Mod Menu 21.0.0 and Jade 26.3.5
- Moved entity models and animations into GeckoLib 5's `geckolib/models` and `geckolib/animations` folders
- Updated entity rendering for the 26.3 render state system and removed mixins that no longer apply
- Updated loot table changes for 26.3 on both loaders (NeoForge global loot modifiers, Fabric loot table events)
- Removed Trinkets support on Fabric, as Trinkets has no release for 26.3 (Curios is still supported on NeoForge)

### Bug Fixes

Fixes for issues and pull requests reported on the original repository:

- Fixed grappling hook crashes when the player dies, disconnects, changes dimension or is teleported while the hook is out, and stopped hooks from travelling through portals (#61, #68, #70, #133, #153, #154, #156, #161, #163)
- Fixed a client crash when a grappling hook update arrives for an unknown entity, such as with Replay Mod or Neruina (#81, #148)
- Fixed otters eating 2–3 food items at once when fed by hand (#191)
- Fixed otters still appearing to hold food after eating it when mods like Lithium are installed (#139)
- Fixed red pandas not being tameable at night while they're asleep (#157)
- Koi fish and clams can no longer be fished up in the Nether or the End (#147)
- Koi fish are now tagged as `c:foods/raw_fish` for compatibility with cooking mods like Farmer's Delight (#134)
- Gold dragonfly armor is now loved by piglins (#128)

### Translations

- Completed the Simplified Chinese translation (#187, thanks to flowersauce)
- Corrected the Russian spelling of "ferret" (#131, thanks to FO13E)
- Added Brazilian Portuguese (#47, thanks to gohkenytp) and Danish (#48, thanks to smifflepuss) translations
