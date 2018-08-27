// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.apache.beam.sdk.io.kinesis;

import org.apache.beam.sdk.io.kinesis.auth.AnonymousAWSCredentialsProvider;
import org.apache.beam.sdk.io.kinesis.auth.BasicAWSCredentialsProvider;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.producer.IKinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;

public class TalendKinesisProvider implements AWSClientsProvider {

    private final boolean specifyCredentials;

    private final String accessKey;

    private final String secretKey;

    private final Regions region;

    private final boolean specifyEndpoint;

    private final String endpoint;

    private final boolean specifySTS;

    private final String roleArn;

    private final String roleSessionName;

    private final boolean specifyRoleExternalId;

    private final String roleExternalId;

    private final boolean specifySTSEndpoint;

    private final String stsEndpoint;

    // TODO add builder
    public TalendKinesisProvider(boolean specifyCredentials, String accessKey, String secretKey,
            boolean specifyEndpoint, String endpoint, Regions region, boolean specifySTS, String roleArn,
            String roleSessionName, boolean specifyRoleExternalId, String roleExternalId, boolean specifySTSEndpoint,
            String stsEndpoint) {
        this.specifyCredentials = specifyCredentials;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.specifyEndpoint = specifyEndpoint;
        this.endpoint = endpoint;
        this.specifySTS = specifySTS;
        this.roleArn = roleArn;
        this.roleSessionName = roleSessionName;
        this.specifyRoleExternalId = specifyRoleExternalId;
        this.roleExternalId = roleExternalId;
        this.specifySTSEndpoint = specifySTSEndpoint;
        this.stsEndpoint = stsEndpoint;
    }

    private AWSCredentialsProviderChain getCredentialsProvier() {
        AWSCredentialsProviderChain credentials = null;
        if (specifyCredentials) {
            credentials = new AWSCredentialsProviderChain(new BasicAWSCredentialsProvider(accessKey, secretKey),
                    new DefaultAWSCredentialsProviderChain(), new AnonymousAWSCredentialsProvider());
        } else {
            // do not be polluted by hidden accessKey/secretKey
            credentials = new AWSCredentialsProviderChain(new DefaultAWSCredentialsProviderChain(),
                    new AnonymousAWSCredentialsProvider());
        }
        if (specifySTS) {
            STSAssumeRoleSessionCredentialsProvider.Builder builder =
                    new STSAssumeRoleSessionCredentialsProvider.Builder(roleArn, roleSessionName)
                            .withLongLivedCredentialsProvider(credentials);
            if (specifyRoleExternalId) {
                builder = builder.withExternalId(roleExternalId);
            }
            if (specifySTSEndpoint) {
                builder = builder.withServiceEndpoint(stsEndpoint);
            }
            credentials = new AWSCredentialsProviderChain(builder.build());
        }
        return credentials;
    }

    @Override
    public AmazonKinesis getKinesisClient() {
        AmazonKinesisClientBuilder clientBuilder =
                AmazonKinesisClientBuilder.standard().withCredentials(getCredentialsProvier());
        if (specifyEndpoint) {
            clientBuilder
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region.getName()));
        } else {
            clientBuilder.setRegion(region.getName());
        }
        return clientBuilder.build();
    }

    @Override
    public AmazonCloudWatch getCloudWatchClient() {
        AmazonCloudWatchClientBuilder clientBuilder =
                AmazonCloudWatchClientBuilder.standard().withCredentials(getCredentialsProvier());
        if (specifyEndpoint) {
            clientBuilder
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region.getName()));
        } else {
            clientBuilder.setRegion(region.getName());
        }
        return clientBuilder.build();
    }

    @Override
    public IKinesisProducer createKinesisProducer(KinesisProducerConfiguration config) {
        // TODO implement when support Kinesis output, as it only used for write
        return null;
    }
}
