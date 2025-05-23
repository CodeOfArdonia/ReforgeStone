# Reforge Stone

This is a mod to add reforge stones, which can combine with other items and improve values.

By default, this mod don't add any reforge stones, you need to define by yourself.

## Define A Reforge Stone

Create a file in `data/<datapack id>/reforge_stone/stone_type/` folder, then write following contents:

```json5
{
  "translate": "block.minecraft.tnt",//Translate in tooltip
  "ingredients": [//combine with which item in anvil to obtain
    "minecraft:tnt"//item or tag
  ],
  "targets": [//which item can obtain this
    "#minecraft:enchantable/sharp_weapon"//item or tag
  ],
  "modifiers": [//the effects after obtain this
    //modifiers, see below
  ]
}
```

### All Available Modifiers

#### Attribute Modifier
```json5
{
  "type": "reforge_stone:attribute",
  "attribute": "minecraft:generic.movement_speed",//target attribute
  "value": "1",//modify value
  "operation": "ADDITION / MULTIPLY_BASE / MULTIPLY_TOTAL"
}
```

### Glint Modifier
```json5
{
  "type": "reforge_stone:glint",
  "color": "red",//color, current available: red, yellow, blue, orange, green, purple, white, pink ,aqua
  "always": true,//(optional) always show or only enchanted
}
```

### Max Damage Modifier
```json5
{
  "type": "reforge_stone:max_damage",
  "color": "addition"
  //durability increment
}
```

### Name Modifier
```json5
{
  "type": "reforge_stone:name",
  "prefix": "",//(optional) predix of item name
  "suffix": "",//(optional) suffix of item name
  "color": -1 //(optional) color of item name in 0xAARRGGBB format
}
```