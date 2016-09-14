package com.google.zxing.integration.android;


/**
 * <p>Encapsulates the result of a barcode scan invoked through {@link IntentIntegrator}.</p>
 *
 * @author Sean Owen
 */
public final class IntentResult {


               private final String contents;
       private final String formatName;
       private final byte[] rawBytes;
       private final Integer orientation;
       private final String errorCorrectionLevel;


               IntentResult() {
             this(null, null, null, null, null);
           }


               IntentResult(String contents,
                                              String formatName,
                                              byte[] rawBytes,
                                              Integer orientation,
                                              String errorCorrectionLevel) {
             this.contents = contents;
             this.formatName = formatName;
             this.rawBytes = rawBytes;
             this.orientation = orientation;
             this.errorCorrectionLevel = errorCorrectionLevel;
           }


               /**
     49    * @return raw content of barcode
     50    */
               public String getContents() {
             return contents;
           }


               /**
     56    * @return name of format, like "QR_CODE", "UPC_A". See {@code BarcodeFormat} for more format names.
     57    */
               public String getFormatName() {
             return formatName;
           }


               /**
     63    * @return raw bytes of the barcode content, if applicable, or null otherwise
     64    */
               public byte[] getRawBytes() {
             return rawBytes;
           }


               /**
     70    * @return rotation of the image, in degrees, which resulted in a successful scan. May be null.
     71    */
               public Integer getOrientation() {
             return orientation;
           }


               /**
     77    * @return name of the error correction level used in the barcode, if applicable
     78    */
               public String getErrorCorrectionLevel() {
             return errorCorrectionLevel;
           }

               @Override
       public String toString() {
             StringBuilder dialogText = new StringBuilder(100);
             dialogText.append("Format: ").append(formatName).append('\n');
             dialogText.append("Contents: ").append(contents).append('\n');
             int rawBytesLength = rawBytes == null ? 0 : rawBytes.length;
             dialogText.append("Raw bytes: (").append(rawBytesLength).append(" bytes)\n");
             dialogText.append("Orientation: ").append(orientation).append('\n');
             dialogText.append("EC level: ").append(errorCorrectionLevel).append('\n');
             return dialogText.toString();
           }

            }

