package com.stackidentity.rae.app.testapps.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AwsS3{
    @JsonProperty("Details")
    public Details getDetails() {
        return this.details; }
    public void setDetails(Details details) {
        this.details = details; }
    Details details;

    @JsonProperty("Acl")
    public Acl getAcl() {
        return this.acl; }
    public void setAcl(Acl acl) {
        this.acl = acl; }
    Acl acl;

    @JsonProperty("Encryption")
    public Encryption getEncryption() {
        return this.encryption; }
    public void setEncryption(Encryption encryption) {
        this.encryption = encryption; }
    Encryption encryption;

    @JsonProperty("Logging")
    public Logging getLogging() {
        return this.logging; }
    public void setLogging(Logging logging) {
        this.logging = logging; }
    Logging logging;

    @JsonProperty("AccessBlock")
    public AccessBlock getAccessBlock() {
        return this.accessBlock; }
    public void setAccessBlock(AccessBlock accessBlock) {
        this.accessBlock = accessBlock; }
    AccessBlock accessBlock;

    @JsonProperty("Location")
    public Location getLocation() {
        return this.location; }
    public void setLocation(Location location) {
        this.location = location; }
    Location location;
}


