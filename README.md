# Reforge Stone

This is a mod to add reforge stones, which can combine with other items and improve values.

By default, this mod don't add any reforge stones, you need to define by yourself.

## Define A Reforge Stone

Create a file in `data/<datapack id>/reforge_stone/stone_type/` folder, then write following contents:

```json5
{
  //Translate in tooltip
  "translate": "block.minecraft.tnt",
  //combine with which item in anvil to obtain
  "ingredients": [
    //item or tag
    "minecraft:tnt"
  ],
  //which item can obtain this
  "targets": [
    //item or tag
    "#minecraft:enchantable/sharp_weapon"
  ],
  //the effects after obtain this
  "modifiers": [
    //modifiers, see below
  ]
}
```

### All Available Modifiers

#### Attribute Modifier

```json5
{
  "type": "reforge_stone:attribute",
  //target attribute
  "attribute": "minecraft:generic.movement_speed",
  //modify value
  "value": "1",
  "operation": "ADDITION / MULTIPLY_BASE / MULTIPLY_TOTAL"
}
```

#### Glint Modifier

```json5
{
  "type": "reforge_stone:glint",
  //color, current available: red, yellow, blue, orange, green, purple, white, pink ,aqua
  "color": "red",
  //(optional) always show or only enchanted
  "always": true,
}
```

#### Max Damage Modifier

```json5
{
  "type": "reforge_stone:max_damage",
  //durability increment
  "color": "addition"
}
```

#### Name Modifier

```json5
{
  "type": "reforge_stone:name",
  //(optional) predix of item name
  "prefix": "",
  //(optional) suffix of item name
  "suffix": "",
  //(optional) color of item name in 0xAARRGGBB format
  "color": -1
}
```

## Additional Function

There are 2 keys to control glint rendering.

- `reforge_stone:glint`: Control the color. Available colors:
  [GlintManager.java](https://github.com/CodeOfArdonia/ReforgeStone/blob/master/common/src/main/java/com/iafenvoy/reforgestone/render/GlintManager.java)
- `reforge_stone:glint_always`: Whether it should always render glint

For example,a diamond sword with red glint:
`/give @s diamond_sword{"reforge_stone:glint":"red","reforge_stone:glint_always":true}`
