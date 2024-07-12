function printCertificate() {
    var printContents = document.getElementById('certificate-container-certificate').outerHTML;
    var originalContents = document.body.innerHTML;
    var printWindow = window.open('', '', 'height=700,width=900');
    printWindow.document.write('<html><head><title>Print Certificate</title>');
    printWindow.document.write('<link rel="stylesheet" type="text/css" href="/css/certificate.css">');
    printWindow.document.write('</head><body>');
    printWindow.document.write(printContents);
    printWindow.document.write('</body></html>');
    printWindow.document.close();
    printWindow.focus();
    printWindow.onload = function() {
        printWindow.print();
        setTimeout(function() {
            printWindow.close();
        }, 500);
    };
}
