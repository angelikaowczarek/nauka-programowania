//Blockly.alert = function(a,b){
//    window.alert(a);
//    };

Blockly.JavaScript['text_print'] = function(block) {
    var text = Blockly.JavaScript.valueToCode(block, 'TEXT',
        Blockly.JavaScript.ORDER_MEMBER) || '\'\'';
    return "theFinalResult += " + text + ";\n";
};