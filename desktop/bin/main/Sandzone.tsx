<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.3.4" name="Sandzone" tilewidth="32" tileheight="32" tilecount="4" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <terraintypes>
  <terrain name="" tile="0"/>
 </terraintypes>
 <tile id="0" type="2">
  <image width="32" height="32" source="sand.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0.094697" y="0.094697" width="31.9129" height="31.9129"/>
  </objectgroup>
 </tile>
 <tile id="2" type="3" terrain=",0,0,0">
  <image width="32" height="32" source="sandhillRight.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="31.25" y="0.378788">
    <polygon points="0,0 -31.1553,31.3447 0.568182,31.5341"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="3" type="1">
  <image width="32" height="32" source="Sky.png"/>
 </tile>
 <tile id="5" type="3">
  <image width="32" height="32" source="sandhillLeft.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0.094697">
    <polygon points="0,0 31.9129,31.9129 0.094697,31.8182"/>
   </object>
  </objectgroup>
 </tile>
</tileset>
