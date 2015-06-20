/*
 * (C) Copyright 2015 Richard Greenlees

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package info.jeremy.voxelengine.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a 3x3 Matrix of floats, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 *      m00  m10  m20</br>
 *      m01  m11  m21</br>
 *      m02  m12  m22</br>
 * 
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3f implements Serializable, Externalizable {

    private static final long serialVersionUID = 1L;

    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;

    /**
     * Create a new {@link Matrix3f} and set it to {@link #identity() identity}.
     */
    public Matrix3f() {
        super();
        identity();
    }

    /**
     * Create a new {@link Matrix3f} and make it a copy of the given matrix.
     * 
     * @param mat
     *          the {@link Matrix3f} to copy the values from
     */
    public Matrix3f(Matrix3f mat) {
        m00 = mat.m00;
        m01 = mat.m01;
        m02 = mat.m02;
        m10 = mat.m10;
        m11 = mat.m11;
        m12 = mat.m12;
        m20 = mat.m20;
        m21 = mat.m21;
        m22 = mat.m22;
    }

    /**
     * Create a new 3x3 matrix using the supplied float values. The order of the parameter is column-major, 
     * so the first three parameters specify the three elements of the first column.
     */
    public Matrix3f(float m00, float m01, float m02, float m10, float m11,
                    float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    /**
     * Set the elements of this matrix to the ones in <code>m</code>.
     * 
     * @param m
     *          the matrix to copy the elements from
     * @return this
     */
    public Matrix3f set(Matrix3f m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        return this;
    }

    /**
     * Set this matrix to be equivalent to the rotation specified by the given {@link AngleAxis4f}.
     * 
     * @param angleAxis
     *          the {@link AngleAxis4f}
     * @return this
     */
    public Matrix3f set(AngleAxis4f angleAxis) {
        float x = angleAxis.x;
        float y = angleAxis.y;
        float z = angleAxis.z;
        double angle = Math.toRadians(angleAxis.angle);
        double n = Math.sqrt(x*x + y*y + z*z);
        n = 1/n;
        x *= n;
        y *= n;
        z *= n;
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double omc = 1.0 - c;
        m00 = (float)(c + x*x*omc);
        m11 = (float)(c + y*y*omc);
        m22 = (float)(c + z*z*omc);
        double tmp1 = x*y*omc;
        double tmp2 = z*s;
        m10 = (float)(tmp1 - tmp2);
        m01 = (float)(tmp1 + tmp2);
        tmp1 = x*z*omc;
        tmp2 = y*s;
        m20 = (float)(tmp1 + tmp2);
        m02 = (float)(tmp1 - tmp2);
        tmp1 = y*z*omc;
        tmp2 = x*s;
        m21 = (float)(tmp1 - tmp2);
        m12 = (float)(tmp1 + tmp2);
        return this;
    }

    /**
     * Set the values of this matrix to the ones of the given javax.vecmath.Matrix3f matrix.
     * 
     * @param javaxVecmathMatrix
     * @return this
     */
//    public Matrix3f fromJavaxMatrix(javax.vecmath.Matrix3f javaxVecmathMatrix) {
//        m00 = javaxVecmathMatrix.m00;
//        m01 = javaxVecmathMatrix.m10;
//        m02 = javaxVecmathMatrix.m20;
//        m10 = javaxVecmathMatrix.m01;
//        m11 = javaxVecmathMatrix.m11;
//        m12 = javaxVecmathMatrix.m21;
//        m20 = javaxVecmathMatrix.m02;
//        m21 = javaxVecmathMatrix.m12;
//        m22 = javaxVecmathMatrix.m22;
//        return this;
//    }

    /**
     * Set the values of this matrix to the ones of the given org.lwjgl.util.vector.Matrix3f matrix.
     * 
     * @param lwjglMatrix
     * @return this
     */
//    public Matrix3f fromLwjglMatrix(org.lwjgl.util.vector.Matrix3f lwjglMatrix) {
//        m00 = lwjglMatrix.m00;
//        m01 = lwjglMatrix.m01;
//        m02 = lwjglMatrix.m02;
//        m10 = lwjglMatrix.m10;
//        m11 = lwjglMatrix.m11;
//        m12 = lwjglMatrix.m12;
//        m20 = lwjglMatrix.m20;
//        m21 = lwjglMatrix.m21;
//        m22 = lwjglMatrix.m22;
//        return this;
//    }

    /**
     * Multiply this matrix by the supplied matrix. This matrix will be the left-sided one.
     * 
     * @param right
     *          the right operand of the matrix multiplication
     * @return this
     */
    public Matrix3f mul(Matrix3f right) {
        mul(this, right, this);
        return this;
    }

    /**
     * Multiply the <code>left</code> matrix by the <code>right</code>, and store the result in <code>dest</code>.
     * 
     * @param left
     *          the left operand
     * @param right
     *          the right operand
     * @param dest
     *          will hold the result
     */
    public static void mul(Matrix3f left, Matrix3f right, Matrix3f dest) {
        if (left != dest && right != dest) {
            dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
            dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
            dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
            dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
            dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
            dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
            dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
            dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
            dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
        } else {
            dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                      left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                      left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                      left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                      left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                      left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                      left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                      left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                      left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
        }
    }
    
    /**
     * Set the values within this matrix to the supplied float values. The result looks like this:
     * <p>
     * m00, m10, m20</br>
     * m01, m11, m21</br>
     * m02, m12, m22</br>
     * 
     * @return this
     */
    public Matrix3f set(float m00, float m01, float m02, float m10, float m11,
                    float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        return this;
    }

    /**
     * Set the values in this matrix based on the supplied float array. The result looks like this:
     * <p>
     * 0, 3, 6</br>
     * 1, 4, 7</br>
     * 2, 5, 8</br>
     * 
     * This method only uses the first 9 values, all others are ignored.
     * 
     * @return this
     */
    public Matrix3f set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
        return this;
    }

    /**
     * Return the determinant of this matrix.
     */
    public float determinant() {
        return m00 * m11 * m22
             + m10 * m21 * m02
             + m20 * m01 * m12
             - m20 * m11 * m02
             - m00 * m21 * m12
             - m10 * m01 * m22;
    }

    /**
     * Invert this matrix.
     *
     * @return this
     */
    public Matrix3f invert() {
        float s = determinant();
        
        if (s == 0.0f) {
            return this;
        }
        s = 1.0f / s;

        return set((m11 * m22) - (m21 * m12) * s,
                 -((m01 * m22) - (m21 * m02)) * s,
                   (m01 * m12) - (m11 * m02) * s,
                 -((m10 * m22) - (m20 * m12)) * s,
                   (m00 * m22) - (m20 * m02) * s,
                 -((m00 * m12) - (m10 * m02)) * s,
                   (m10 * m21) - (m20 * m11) * s,
                 -((m00 * m21) - (m20 * m01)) * s,
                   (m00 * m11) - (m10 * m01) * s);
    }
    
    /**
     * Invert the <code>source</code> matrix and store the result in <code>dest</code>.
     */
    public static void invert(Matrix3f source, Matrix3f dest) {
        float s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        if (source != dest) {
            dest.m00 = ((source.m11 * source.m22) - (source.m21 * source.m12)) * s;
            dest.m01 = -((source.m01 * source.m22) - (source.m21 * source.m02)) * s;
            dest.m02 = ((source.m01 * source.m12) - (source.m11 * source.m02)) * s;
            dest.m10 = -((source.m10 * source.m22) - (source.m20 * source.m12)) * s;
            dest.m11 = ((source.m00 * source.m22) - (source.m20 * source.m02)) * s;
            dest.m12 = -((source.m00 * source.m12) - (source.m10 * source.m02)) * s;
            dest.m20 = ((source.m10 * source.m21) - (source.m20 * source.m11)) * s;
            dest.m21 = -((source.m00 * source.m21) - (source.m20 * source.m01)) * s;
            dest.m22 = ((source.m00 * source.m11) - (source.m10 * source.m01)) * s;
        } else {
            dest.set(  ((source.m11 * source.m22) - (source.m21 * source.m12)) * s,
                      -((source.m01 * source.m22) - (source.m21 * source.m02)) * s,
                       ((source.m01 * source.m12) - (source.m11 * source.m02)) * s,
                      -((source.m10 * source.m22) - (source.m20 * source.m12)) * s,
                       ((source.m00 * source.m22) - (source.m20 * source.m02)) * s,
                      -((source.m00 * source.m12) - (source.m10 * source.m02)) * s,
                       ((source.m10 * source.m21) - (source.m20 * source.m11)) * s,
                      -((source.m00 * source.m21) - (source.m20 * source.m01)) * s,
                       ((source.m00 * source.m11) - (source.m10 * source.m01)) * s  );
        }
    }
    
    /**
     * Transpose this matrix.
     *
     * @return this
     */
    public Matrix3f transpose() {
        transpose(this, this);
        return this;
    }
    
    /**
     * Transpose the supplied <code>original</code> matrix and store the result in <code>dest</code>.
     */
    public static void transpose(Matrix3f original, Matrix3f dest) {
        if (original != dest) {
            dest.m00 = original.m00;
            dest.m01 = original.m10;
            dest.m02 = original.m20;
            dest.m10 = original.m01;
            dest.m11 = original.m11;
            dest.m12 = original.m21;
            dest.m20 = original.m02;
            dest.m21 = original.m12;
            dest.m22 = original.m22;
        } else {
            dest.set(original.m00, original.m10, original.m20,
                     original.m01, original.m11, original.m21,
                     original.m02, original.m12, original.m22);
        }
    }
    
    /**
     * Set this matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     * 
     * @return this
     */
    public Matrix3f translation(float x, float y) {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = x;
        m21 = y;
        m22 = 1.0f;
        return this;
    }

    /**
     * Set the given matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(Vector2f position, Matrix3f dest) {
        dest.translation(position.x, position.y);
    }

    /**
     * Set the given matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(float x, float y, Matrix3f dest) {
        dest.translation(x, y);
    }

    /**
     * Set this matrix to be a simple translation matrix in a two-dimensional coordinate system.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public Matrix3f translation(Vector2f position) {
        return translation(position.x, position.y);
    }
    
    /**
     * Return a string representation of this matrix.
     * <p>
     * This method creates a new {@link DecimalFormat} on every invocation with the format string "<tt>  0.000E0; -</tt>".
     * 
     * @return the string representation
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("  0.000E0; -");
        return toString(formatter).replaceAll("E(\\d+)", "E+$1");
    }

    /**
     * Return a string representation of this matrix by formatting the matrix elements with the given {@link NumberFormat}.
     * 
     * @param formatter
     *          the {@link NumberFormat} used to format the matrix values with
     * @return the string representation
     */
    public String toString(NumberFormat formatter) {
        return formatter.format(m00) + formatter.format(m10) + formatter.format(m20) + "\n"
             + formatter.format(m01) + formatter.format(m11) + formatter.format(m21) + "\n"
             + formatter.format(m02) + formatter.format(m12) + formatter.format(m22) + "\n";
    }

    /**
     * Get the current values of <code>this</code> matrix and store them into
     * <code>dest</code>.
     * <p>
     * This is the reverse method of {@link #set(Matrix3f)} and allows to obtain
     * intermediate calculation results when chaining multiple transformations.
     * 
     * @param dest
     *          the destination matrix
     * @return this
     */
    public Matrix3f get(Matrix3f dest) {
        dest.set(this);
        return this;
    }

    /**
     * Get the current values of <code>this</code> matrix and store them as
     * the rotational component of <code>dest</code>. All other values of <code>dest</code> will
     * be set to identity.
     * 
     * @param dest
     *          the destination matrix
     * @return this
     */
    public Matrix3f get(Matrix4f dest) {
        dest.set(this);
        return this;
    }

    /**
     * Get the current values of <code>this</code> matrix and store the represented rotation
     * into the given {@link AngleAxis4f}.
     * 
     * @param dest
     *          the destination {@link AngleAxis4f}
     * @return this
     */
    public Matrix3f get(AngleAxis4f dest) {
        dest.set(this);
        return this;
    }

    /**
     * Store this matrix into the supplied {@link FloatBuffer} at the current
     * buffer {@link FloatBuffer#position() position}.
     * <p>
     * This method will not increment the position of the given
     * {@link FloatBuffer}.
     * <p>
     * If you want to specify the offset into the {@link FloatBuffer} at which
     * the matrix is stored, you can use {@link #get(int, FloatBuffer)}, taking
     * the absolute position as parameter.
     * 
     * @see #get(int, FloatBuffer)
     * 
     * @param buffer
     *            will receive the values of this matrix in column-major order at its current position
     * @return this
     */
    public Matrix3f get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    /**
     * Store this matrix into the supplied {@link FloatBuffer} starting at the specified
     * absolute buffer position/index.
     * <p>
     * This method will not increment the position of the given {@link FloatBuffer}.
     * 
     * @param index
     *            the absolute position into the {@link FloatBuffer}
     * @param buffer
     *            will receive the values of this matrix in column-major order
     * @return this
     */
    public Matrix3f get(int index, FloatBuffer buffer) {
        buffer.put(index, m00);
        buffer.put(index+1, m01);
        buffer.put(index+2, m02);
        buffer.put(index+3, m10);
        buffer.put(index+4, m11);
        buffer.put(index+5, m12);
        buffer.put(index+6, m20);
        buffer.put(index+7, m21);
        buffer.put(index+8, m22);
        return this;
    }

    /**
     * Set the values of this matrix by reading 9 float values from the given FloatBuffer,
     * starting at its current position.
     * <p>
     * The FloatBuffer is expected to contain the values in column-major order.
     * <p>
     * The position of the FloatBuffer will not be changed by this method.
     * 
     * @return this
     */
    public Matrix3f set(FloatBuffer buffer) {
        int pos = buffer.position();
        m00 = buffer.get(pos);
        m01 = buffer.get(pos+1);
        m02 = buffer.get(pos+2);
        m10 = buffer.get(pos+3);
        m11 = buffer.get(pos+4);
        m12 = buffer.get(pos+5);
        m20 = buffer.get(pos+6);
        m21 = buffer.get(pos+7);
        m22 = buffer.get(pos+8);
        return this;
    }

    /**
     * Set all values within this matrix to zero.
     * 
     * @return this
     */
    public Matrix3f zero() {
        m00 = 0.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 0.0f;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 0.0f;
        return this;
    }
    
    /**
     * Set this matrix to the identity.
     * 
     * @return this
     */
    public Matrix3f identity() {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        return this;
    }

    /**
     * Apply scaling to this matrix by scaling the unit axes by the given x,
     * y and z factors.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @param x
     *            the factor of the x component
     * @param y
     *            the factor of the y component
     * @param z
     *            the factor of the z component
     * @return this
     */
    public Matrix3f scale(float x, float y, float z) {
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // all others = 0
        m00 = m00 * x;
        m01 = m01 * x;
        m02 = m02 * x;
        m10 = m10 * y;
        m11 = m11 * y;
        m12 = m12 * y;
        m20 = m20 * z;
        m21 = m21 * z;
        m22 = m22 * z;
        return this;
    }

    /**
     * Apply scaling to this matrix by uniformly scaling all unit axes by the given <code>xyz</code> factor.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>S</code> the scaling matrix,
     * then the new matrix will be <code>M * S</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * S * v</code>
     * , the scaling will be applied first!
     * 
     * @see #scale(float, float, float)
     * 
     * @param xyz
     *            the factor for all components
     * @return this
     */
    public Matrix3f scale(float xyz) {
        return scale(xyz, xyz, xyz);
    }

    /**
     * Set the given matrix <code>dest</code> to be a simple scale matrix.
     * 
     * @param scale
     * 			the scale applied to each dimension
     */
    public static void scaling(Vector3f scale, Matrix3f dest) {
    	dest.identity();
        dest.m00 = scale.x;
        dest.m11 = scale.y;
        dest.m22 = scale.z;
    }
    
    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     * 			the scale in x
     * @param y
     * 			the scale in y
     * @param z
     * 			the scale in z
     * @return this
     */
    public Matrix3f scaling(float x, float y, float z, Matrix3f dest) {
    	dest.identity();
    	dest.m00 = x;
    	dest.m11 = y;
    	dest.m22 = z;
    	return this;
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given degrees about a given axis.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * If you want to post-multiply a rotation transformation directly to a
     * matrix, you can use {@link #rotate(float, Vector3f) rotate()} instead.
     * 
     * @see #rotate(float, Vector3f)
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the axis to rotate about (needs to be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix3f rotation(float angle, Vector3f axis) {
        return rotation(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Set this matrix to a rotation transformation using the given {@link AngleAxis4f}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(AngleAxis4f) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     *
     * @see #rotate(AngleAxis4f)
     * 
     * @param angleAxis
     *          the {@link AngleAxis4f} (needs to be {@link AngleAxis4f#normalize() normalized})
     * @return this
     */
    public Matrix3f rotation(AngleAxis4f angleAxis) {
        return rotation(angleAxis.angle, angleAxis.x, angleAxis.y, angleAxis.z);
    }

    /**
     * Set this matrix to a rotation matrix which rotates the given degrees about a given axis.
     * <p>
     * The axis described by the three components needs to be a unit vector.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(float, float, float, float) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
     * 
     * @param angle
     *          the angle in degrees
     * @param x
     *          the x-component of the rotation axis
     * @param y
     *          the y-component of the rotation axis
     * @param z
     *          the z-component of the rotation axis
     * @return this
     */
    public Matrix3f rotation(float angle, float x, float y, float z) {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        float C = 1.0f - cos;
        m00 = cos + x * x * C;
        m10 = x * y * C - z * sin;
        m20 = x * z * C + y * sin;
        m01 = y * x * C + z * sin;
        m11 = cos + y * y * C;
        m21 = y * z * C - x * sin;
        m02 = z * x * C - y * sin;
        m12 = z * y * C + x * sin;
        m22 = cos + z * z * C;
        return this;
    }

    /**
     * Set the destination matrix to a rotation matrix which rotates the given degrees about the specified axis.
     * The result will be stored in <code>dest</code>.
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the axis to rotate about
     * @param dest
     *          will hold the result
     */
    public static void rotation(float angle, Vector3f axis, Matrix3f dest) {
        dest.rotation(angle, axis);
    }

    /**
     * Set this matrix to a rotation transformation about the X axis.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix3f rotationX(float ang) {
        float cos = (float) Math.cos(Math.toRadians(ang));
        float sin = (float) Math.sin(Math.toRadians(ang));
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = cos;
        m12 = sin;
        m20 = 0.0f;
        m21 = -sin;
        m22 = cos;
        return this;
    }

    /**
     * Set this matrix to a rotation transformation about the Y axis.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix3f rotationY(float ang) {
        float cos = (float) Math.cos(Math.toRadians(ang));
        float sin = (float) Math.sin(Math.toRadians(ang));
        m00 = cos;
        m01 = 0.0f;
        m02 = -sin;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = sin;
        m21 = 0.0f;
        m22 = cos;
        return this;
    }

    /**
     * Set this matrix to a rotation transformation about the Z axis.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix3f rotationZ(float ang) {
        float cos = (float) Math.cos(Math.toRadians(ang));
        float sin = (float) Math.sin(Math.toRadians(ang));
        m00 = cos;
        m01 = sin;
        m02 = 0.0f;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 0.0f;
        return this;
    }

    /**
     * Set this matrix to the rotation transformation of the given {@link Quaternion}.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional rotation.
     * <p>
     * In order to apply the rotation transformation to an existing transformation,
     * use {@link #rotate(Quaternion) rotate()} instead.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotate(Quaternion)
     * 
     * @param quat
     *          the {@link Quaternion}
     * @return this
     */
    public Matrix3f rotation(Quaternion quat) {
        float q00 = 2.0f * quat.x * quat.x;
        float q11 = 2.0f * quat.y * quat.y;
        float q22 = 2.0f * quat.z * quat.z;
        float q01 = 2.0f * quat.x * quat.y;
        float q02 = 2.0f * quat.x * quat.z;
        float q03 = 2.0f * quat.x * quat.w;
        float q12 = 2.0f * quat.y * quat.z;
        float q13 = 2.0f * quat.y * quat.w;
        float q23 = 2.0f * quat.z * quat.w;

        m00 = 1.0f - q11 - q22;
        m01 = q01 + q23;
        m02 = q02 - q13;
        m10 = q01 - q23;
        m11 = 1.0f - q22 - q00;
        m12 = q12 + q03;
        m20 = q02 + q13;
        m21 = q12 - q03;
        m22 = 1.0f - q11 - q00;

        return this;
    }

    /**
     * Transform the given vector by this matrix.
     * 
     * @param v
     *          the vector to transform
     * @return this
     */
    public Matrix3f transform(Vector3f v) {
        v.mul(this);
        return this;
    }

    /**
     * Transform the given vector by this matrix and store the result in <code>dest</code>.
     * 
     * @param v
     *          the vector to transform
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix3f transform(Vector3f v, Vector3f dest) {
        v.mul(this, dest);
        return this;
    }

    /**
     * Transform the given vector by the given matrix.
     * 
     * @param mat
     *          the matrix to transform the vector by
     * @param v
     *          the vector to transform
     */
    public static void transform(Matrix3f mat, Vector3f v) {
        v.mul(mat);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(m00);
        out.writeFloat(m01);
        out.writeFloat(m02);
        out.writeFloat(m10);
        out.writeFloat(m11);
        out.writeFloat(m12);
        out.writeFloat(m20);
        out.writeFloat(m21);
        out.writeFloat(m22);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00 = in.readFloat();
        m01 = in.readFloat();
        m02 = in.readFloat();
        m10 = in.readFloat();
        m11 = in.readFloat();
        m12 = in.readFloat();
        m20 = in.readFloat();
        m21 = in.readFloat();
        m22 = in.readFloat();
    }

    /**
     * Apply rotation about the X axis to this matrix by rotating the given amount of degrees.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix3f rotateX(float ang) {
        float cos = (float) Math.cos(Math.toRadians(ang));
        float sin = (float) Math.sin(Math.toRadians(ang));
        float rm11 = cos;
        float rm21 = -sin;
        float rm12 = sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        m20 = m10 * rm21 + m20 * rm22;
        m21 = m11 * rm21 + m21 * rm22;
        m22 = m12 * rm21 + m22 * rm22;
        // set other values
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;

        return this;
    }

    /**
     * Apply rotation about the Y axis to this matrix by rotating the given amount of degrees.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix3f rotateY(float ang) {
        float cos = (float) Math.cos(Math.toRadians(ang));
        float sin = (float) Math.sin(Math.toRadians(ang));
        float rm00 = cos;
        float rm20 = sin;
        float rm02 = -sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        // set non-dependent values directly
        m20 = m00 * rm20 + m20 * rm22;
        m21 = m01 * rm20 + m21 * rm22;
        m22 = m02 * rm20 + m22 * rm22;
        // set other values
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;

        return this;
    }

    /**
     * Apply rotation about the Z axis to this matrix by rotating the given amount of degrees.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @return this
     */
    public Matrix3f rotateZ(float ang) {
        float cos = (float) Math.cos(Math.toRadians(ang));
        float sin = (float) Math.sin(Math.toRadians(ang));
        float rm00 = cos;
        float rm10 = -sin;
        float rm01 = sin;
        float rm11 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        float nm10 = m00 * rm10 + m10 * rm11;
        float nm11 = m01 * rm10 + m11 * rm11;
        float nm12 = m02 * rm10 + m12 * rm11;
        // set other values
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;

        return this;
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of degrees
     * about the given axis specified as x, y and z components.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @param x
     *            the x component of the axis
     * @param y
     *            the y component of the axis
     * @param z
     *            the z component of the axis
     * @return this
     */
    public Matrix3f rotate(float ang, float x, float y, float z) {
        return rotate(ang, x, y, z, this);
    }

    /**
     * Apply rotation to this matrix by rotating the given amount of degrees
     * about the given axis specified as x, y and z components, and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>R</code> the rotation matrix,
     * then the new matrix will be <code>M * R</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * R * v</code>
     * , the rotation will be applied first!
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @param ang
     *            the angle in degrees
     * @param x
     *            the x component of the axis
     * @param y
     *            the y component of the axis
     * @param z
     *            the z component of the axis
     * @param dest
     *            will hold the result
     * @return this
     */
    public Matrix3f rotate(float ang, float x, float y, float z, Matrix3f dest) {
        float s = (float) Math.sin(Math.toRadians(ang));
        float c = (float) Math.cos(Math.toRadians(ang));
        float C = 1.0f - c;

        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        float rm00 = x * x * C + c;
        float rm01 = y * x * C + z * s;
        float rm02 = z * x * C - y * s;
        float rm10 = x * y * C - z * s;
        float rm11 = y * y * C + c;
        float rm12 = z * y * C + x * s;
        float rm20 = x * z * C + y * s;
        float rm21 = y * z * C - x * s;
        float rm22 = z * z * C + c;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return this;
    }

    /**
     * Apply the rotation transformation of the given {@link Quaternion} to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaternion)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaternion)
     * 
     * @param quat
     *          the {@link Quaternion}
     * @return this
     */
    public Matrix3f rotate(Quaternion quat) {
        return rotate(quat, this);
    }

    /**
     * Apply the rotation transformation of the given {@link Quaternion} to this matrix and store
     * the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>Q</code> the rotation matrix obtained from the given quaternion,
     * then the new matrix will be <code>M * Q</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * Q * v</code>,
     * the quaternion rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(Quaternion)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Quaternion">http://en.wikipedia.org</a>
     * 
     * @see #rotation(Quaternion)
     * 
     * @param quat
     *          the {@link Quaternion}
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix3f rotate(Quaternion quat, Matrix3f dest) {
        float q00 = 2.0f * quat.x * quat.x;
        float q11 = 2.0f * quat.y * quat.y;
        float q22 = 2.0f * quat.z * quat.z;
        float q01 = 2.0f * quat.x * quat.y;
        float q02 = 2.0f * quat.x * quat.z;
        float q03 = 2.0f * quat.x * quat.w;
        float q12 = 2.0f * quat.y * quat.z;
        float q13 = 2.0f * quat.y * quat.w;
        float q23 = 2.0f * quat.z * quat.w;

        float rm00 = 1.0f - q11 - q22;
        float rm01 = q01 + q23;
        float rm02 = q02 - q13;
        float rm10 = q01 - q23;
        float rm11 = 1.0f - q22 - q00;
        float rm12 = q12 + q03;
        float rm20 = q02 + q13;
        float rm21 = q12 - q03;
        float rm22 = 1.0f - q11 - q00;

        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        dest.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        dest.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;

        return this;
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AngleAxis4f}, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle-axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the angle-axis rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AngleAxis4f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
     * @see #rotation(AngleAxis4f)
     * 
     * @param angleAxis
     *          the {@link AngleAxis4f} (needs to be {@link AngleAxis4f#normalize() normalized})
     * @return this
     */
    public Matrix3f rotate(AngleAxis4f angleAxis) {
        return rotate(angleAxis.angle, angleAxis.x, angleAxis.y, angleAxis.z);
    }

    /**
     * Apply a rotation transformation, rotating about the given {@link AngleAxis4f} and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle-axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the angle-axis rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(AngleAxis4f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
     * @see #rotation(AngleAxis4f)
     * 
     * @param angleAxis
     *          the {@link AngleAxis4f} (needs to be {@link AngleAxis4f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix3f rotate(AngleAxis4f angleAxis, Matrix3f dest) {
        return rotate(angleAxis.angle, angleAxis.x, angleAxis.y, angleAxis.z, dest);
    }

    /**
     * Apply a rotation transformation, rotating the given degree about the specified axis, to this matrix.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle-axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the angle-axis rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(float, Vector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
     * @see #rotation(float, Vector3f)
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @return this
     */
    public Matrix3f rotate(float angle, Vector3f axis) {
        return rotate(angle, axis.x, axis.y, axis.z);
    }

    /**
     * Apply a rotation transformation, rotating the given degree about the specified axis and store the result in <code>dest</code>.
     * <p>
     * If <code>M</code> is <code>this</code> matrix and <code>A</code> the rotation matrix obtained from the given angle-axis,
     * then the new matrix will be <code>M * A</code>. So when transforming a
     * vector <code>v</code> with the new matrix by using <code>M * A * v</code>,
     * the angle-axis rotation will be applied first!
     * <p>
     * In order to set the matrix to a rotation transformation without post-multiplying,
     * use {@link #rotation(float, Vector3f)}.
     * <p>
     * Reference: <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Axis_and_angle">http://en.wikipedia.org</a>
     * 
     * @see #rotate(float, float, float, float)
     * @see #rotation(float, Vector3f)
     * 
     * @param angle
     *          the angle in degrees
     * @param axis
     *          the rotation axis (needs to be {@link Vector3f#normalize() normalized})
     * @param dest
     *          will hold the result
     * @return this
     */
    public Matrix3f rotate(float angle, Vector3f axis, Matrix3f dest) {
        return rotate(angle, axis.x, axis.y, axis.z, dest);
    }

    /**
     * Get the row at the given <code>row</code> index, starting with <code>0</code>.
     * 
     * @param row
     *          the row index in <tt>[0..2]</tt>
     * @param dest
     *          will hold the row components
     * @throws IndexOutOfBoundsException if <code>row</code> is not in <tt>[0..2]</tt>
     */
    public void getRow(int row, Vector3f dest) throws IndexOutOfBoundsException {
        switch (row) {
        case 0:
            dest.x = m00;
            dest.y = m10;
            dest.z = m20;
            break;
        case 1:
            dest.x = m01;
            dest.y = m11;
            dest.z = m21;
            break;
        case 2:
            dest.x = m02;
            dest.y = m12;
            dest.z = m22;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return;
    }

    /**
     * Get the column at the given <code>column</code> index, starting with <code>0</code>.
     * 
     * @param column
     *          the column index in <tt>[0..2]</tt>
     * @param dest
     *          will hold the column components
     * @throws IndexOutOfBoundsException if <code>column</code> is not in <tt>[0..2]</tt>
     */
    public void getColumn(int column, Vector3f dest) throws IndexOutOfBoundsException {
        switch (column) {
        case 0:
            dest.x = m00;
            dest.y = m01;
            dest.z = m02;
            break;
        case 1:
            dest.x = m10;
            dest.y = m11;
            dest.z = m12;
            break;
        case 2:
            dest.x = m20;
            dest.y = m21;
            dest.z = m22;
            break;
        default:
            throw new IndexOutOfBoundsException();
        }
        return;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * <p>
     * This method exists to switch the matrix that subsequent calls operate on when using
     * method chaining in a fluent interface style.
     * 
     * @param other
     *          the {@link Matrix3f} to return
     * @return that matrix
     */
    public Matrix3f with(Matrix3f other) {
        return other;
    }

    /**
     * Return the specified {@link Matrix3f}.
     * <p>
     * This method mainly exists for symmetry with operation chaining when using the
     * {@link #with(Matrix3f)} instance call in a fluent interface style.
     * <p>
     * It purposely breaks with Java's camelCase naming convention.
     * 
     * @param other
     *          the {@link Matrix3f} to return
     * @return that matrix
     */
    public static Matrix3f With(Matrix3f other) {
        return other;
    }

}
