(function() {
    window.webkit = { 'messageHandlers': { 'interOp': window.interOp } };
    console.log('Matched webkit interface');
})()