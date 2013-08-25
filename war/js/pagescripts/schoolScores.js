$(document).ready(function() {
	$('button').on('click', function() {
		$('button').hide();
		window.print();
		$('button').show();
	});
	$("table").tablesorter({sortList: [[0,0]]});
	
	$('td').hover(function() {
	    var t = parseInt($(this).index()) + 1;
	    $('td:nth-child(' + t + '):not(tfoot td)', $(this).closest('table')).addClass('highlighted');
	}, function() {
	    var t = parseInt($(this).index()) + 1;
	    $('td:nth-child(' + t + '):not(tfoot td)', $(this).closest('table')).removeClass('highlighted');
	});
});