function eliminar(id) {
	swal({
		title: '¿Seguro que quieres eliminar el registro?',
		text: "No podrás deshacer los cambios.",
		icon: 'warning',
		buttons: true,
		dangerMode: true
	}).then((result) => {
		if (result) {
			$.ajax({
				url: "/eliminarOrdenadores/" + id,
				success: function(res) {
					console.log(res);
				}
			});
			swal("Has borrado el registro", {
				icon: success
			}).then((ok) => {
				if (ok) {
					location.href="/loggedIndex";
				}
			});
		}
	})
}